package com.example.PersonalAccounting.services;

import com.example.PersonalAccounting.model.FinancialArrangement;
import com.example.PersonalAccounting.model.User;
import com.example.PersonalAccounting.model.enums.FinancialArrangementState;
import com.example.PersonalAccounting.model.enums.Status;
import com.example.PersonalAccounting.repositories.FinancialArrangementRepository;
import com.example.PersonalAccounting.services.finantial_arrangement_calculations.FinancialArrangementCalculations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialArrangementService {

    private final FinancialArrangementRepository financialArrangementRepository;
    private final List<FinancialArrangementCalculations> financialArrangementCalculationsList;

    @Autowired
    public FinancialArrangementService(FinancialArrangementRepository financialArrangementRepository,
                                       List<FinancialArrangementCalculations> financialArrangementCalculationsList) {
        this.financialArrangementRepository = financialArrangementRepository;
        this.financialArrangementCalculationsList = financialArrangementCalculationsList;
    }

    @Transactional
    public void create(FinancialArrangement financialArrangement, User user) {
        financialArrangement.setUser(user);
        user.addFinancialArrangement(financialArrangement);

        //TODO: find a prettier way to choose FinancialArrangementCalculations
        FinancialArrangementCalculations calculations = getCalculationsByState(financialArrangement.getState());

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
        FinancialArrangement arrangement = financialArrangementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));

        //TODO: find a prettier way to choose FinancialArrangementCalculations
        FinancialArrangementCalculations calculations = getCalculationsByState(arrangement.getState());

        arrangement.setRefundSum(calculations.calculateRefundSum(arrangement));
        if(calculations.isOutOfDate(arrangement)) arrangement.setStatus(Status.OVERDUE);

        return arrangement;
    }

    @Transactional
    public void update(int id, FinancialArrangement financialArrangement) throws IllegalArgumentException{
        financialArrangementRepository.findById(id).ifPresentOrElse(fa ->
        {
            fa.setName(financialArrangement.getName());
            fa.setCurrentSum(financialArrangement.getCurrentSum());
            fa.setPercent(financialArrangement.getPercent());
            fa.setEndDate(financialArrangement.getEndDate());
            fa.setStatus(Status.ACTIVE);
        }, () -> {
            throw new IllegalArgumentException("Invalid id: " + id);
        });
    }

    @Transactional
    public void delete(int id) {
        financialArrangementRepository.deleteById(id);
    }


    //TODO: add transaction creating
    @Transactional
    public void makePayment(int id) {
        FinancialArrangement financialArrangement = financialArrangementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No financial arrangement with such id: "));

        //TODO: find a prettier way to choose FinancialArrangementCalculations
        FinancialArrangementCalculations calculations = getCalculationsByState(financialArrangement.getState());

        calculations.makePayment(financialArrangement);
        if(calculations.isFullyRepaid(financialArrangement)) financialArrangement.setStatus(Status.EXECUTED);

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
