package com.example.PersonalAccounting.services;

import com.example.PersonalAccounting.model.Accumulation;
import com.example.PersonalAccounting.model.Transaction;
import com.example.PersonalAccounting.model.User;
import com.example.PersonalAccounting.model.enums.Status;
import com.example.PersonalAccounting.repositories.AccumulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccumulationService {

    private final AccumulationRepository accumulationRepository;
    private final TransactionService transactionService;

    @Autowired
    public AccumulationService(AccumulationRepository accumulationRepository,
                               TransactionService transactionService) {
        this.accumulationRepository = accumulationRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public void create(Accumulation accumulation, User user) {
        accumulation.setUser(user);
        user.addAccumulation(accumulation);

        accumulation.setStartDate(LocalDate.now());
        accumulation.setStatus(Status.ACTIVE);
        accumulationRepository.save(accumulation);
    }

    public List<Accumulation> getAll() {
        return accumulationRepository.findAll();
    }

    public Optional<Accumulation> getOne(int id) {
        return accumulationRepository.findById(id);
    }

    @Transactional
    public void update(int id, Accumulation accumulation) {
        accumulationRepository.findById(id).ifPresentOrElse(a -> {
            a.setName(accumulation.getName());
            a.setCurrentSum(accumulation.getCurrentSum());
            a.setGoalSum(accumulation.getGoalSum());
            a.setComment(accumulation.getComment());
            a.setEndDate(accumulation.getEndDate());
            a.setStatus(Status.ACTIVE);
        }, () -> {
            throw new IllegalArgumentException("No accumulation with id: " + id);
        });
    }

    @Transactional
    public void delete(int id) {
        accumulationRepository.deleteById(id);
    }

    //TODO: add method that will be check last payment, and if it was long ago change state to OVERDUE


    @Transactional
    public void makePayment(int id, Transaction transaction, User user) {
        Accumulation accumulation = accumulationRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("No accumulation with such id: " + id));

        accumulation.setStatus(Status.ACTIVE);
        accumulation.setCurrentSum(accumulation.getCurrentSum() + transaction.getSum());

        update(id, accumulation);
        transactionService.create(transaction, user);
    }
}
