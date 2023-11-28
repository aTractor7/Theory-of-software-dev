package com.example.PersonalAccounting.services.crud_seervice_impl;

import com.example.PersonalAccounting.entity.FinancialArrangement;
import com.example.PersonalAccounting.entity.Transaction;
import com.example.PersonalAccounting.entity.User;
import com.example.PersonalAccounting.entity.enums.FinancialArrangementState;
import com.example.PersonalAccounting.entity.enums.Status;
import com.example.PersonalAccounting.repositories.FinancialArrangementRepository;
import com.example.PersonalAccounting.services.CrudService;
import com.example.PersonalAccounting.services.finantial_arrangement_calculations.FinancialArrangementCalculations;
import com.example.PersonalAccounting.util.exceptions.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FinancialArrangementService implements CrudService<FinancialArrangement> {

    private final FinancialArrangementRepository financialArrangementRepository;
    private final TransactionService transactionService;
    private final List<FinancialArrangementCalculations> financialArrangementCalculationsList;

    @Autowired
    public FinancialArrangementService(FinancialArrangementRepository financialArrangementRepository,
                                       TransactionService transactionService, List<FinancialArrangementCalculations> financialArrangementCalculationsList) {
        this.financialArrangementRepository = financialArrangementRepository;
        this.transactionService = transactionService;
        this.financialArrangementCalculationsList = financialArrangementCalculationsList;
    }


    //TODO: add bool param that will be tell make or no make transaction while creation or executing arrangement

    @Transactional
    public void create(FinancialArrangement financialArrangement) {
        User user = financialArrangement.getUser();
        user.addFinancialArrangement(financialArrangement);

        //TODO: find a prettier way to choose FinancialArrangementCalculations
        FinancialArrangementCalculations calculations = getCalculationsByState(financialArrangement.getState());

        Transaction transaction =
                calculations.createStartTransaction(financialArrangement, financialArrangement.getUser());
        if (!transaction.isEmpty()) transactionService.create(transaction);
        financialArrangement.setCurrentSum(calculations.calculateCurrentSumInitValue(financialArrangement));
        financialArrangement.setStartDate(LocalDate.now());
        financialArrangement.setStatus(Status.ACTIVE);
        financialArrangementRepository.save(financialArrangement);
    }

    @Transactional(readOnly = true)
    public List<FinancialArrangement> getAll() {
        return financialArrangementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public FinancialArrangement getOne(int id) {
        FinancialArrangement arrangement = getOneNoCalculation(id);

        //TODO: find a prettier way to choose FinancialArrangementCalculations
        FinancialArrangementCalculations calculations = getCalculationsByState(arrangement.getState());

        arrangement.setRefundSum(calculations.calculateRefundSum(arrangement));
        if(calculations.isOutOfDate(arrangement)) arrangement.setStatus(Status.OVERDUE);
        else arrangement.setStatus(Status.ACTIVE);

        return arrangement;
    }

    private FinancialArrangement getOneNoCalculation(int id){
        return financialArrangementRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invalid id: " + id));
    }

    @Transactional
    public void update(int id, FinancialArrangement financialArrangement) throws IllegalArgumentException{
        financialArrangementRepository.findById(id).ifPresentOrElse(fa ->
        {
            fa.setName(financialArrangement.getName());
            fa.setCurrentSum(financialArrangement.getCurrentSum());
            fa.setPercent(financialArrangement.getPercent());
            fa.setEndDate(financialArrangement.getEndDate());
            if(financialArrangement.getStatus() != null) {
                fa.setStatus(financialArrangement.getStatus());
            } else {
                fa.setStatus(Status.ACTIVE);
            }
        }, () -> {
            throw new NoSuchElementException("Invalid id: " + id);
        });
    }

    @Transactional
    public void delete(int id) {
        financialArrangementRepository.deleteById(id);
    }

    @Transactional
    public void makePayment(int id) {
        //TODO:Write logs
        FinancialArrangement financialArrangement = getOneNoCalculation(id);

        if(financialArrangement.getStatus().equals(Status.EXECUTED))
            throw new PaymentException("Financial arrangement  is executed");

        //TODO: find a prettier way to choose FinancialArrangementCalculations
        FinancialArrangementCalculations calculations = getCalculationsByState(financialArrangement.getState());

        calculations.makePayment(financialArrangement);
        Transaction paymentTransaction = calculations.createPaymentTransaction(financialArrangement,
                financialArrangement.getUser());
        if(!paymentTransaction.isEmpty())
            transactionService.create(paymentTransaction);
        if(calculations.isFullyRepaid(financialArrangement)) {
            Transaction endTransaction =
                    calculations.createEndTransaction(financialArrangement, financialArrangement.getUser());
            if(!endTransaction.isEmpty())
                transactionService.create(endTransaction);
            financialArrangement.setStatus(Status.EXECUTED);
        }

        update(id, financialArrangement);
    }

    private FinancialArrangementCalculations getCalculationsByState(FinancialArrangementState state)
            throws IllegalArgumentException{
        return financialArrangementCalculationsList.stream()
                .filter(c -> c.operatedState() != null)
                .filter(c -> c.operatedState().equals(state))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("No such state"));
    }

}
