package com.example.PersonalAccounting.services.crud_seervice_impl;

import com.example.PersonalAccounting.entity.User;
import com.example.PersonalAccounting.repositories.TransactionRepository;
import com.example.PersonalAccounting.entity.Transaction;
import com.example.PersonalAccounting.services.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService implements CrudService<Transaction> {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void create(Transaction transaction) {
        User user = transaction.getUser();
        user.addTransaction(transaction);

        userFundsAddTransactions(transaction.getUser(), transaction);

        transaction.setDateTime(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Transaction getOne(int id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No transaction with such id: " + id));
    }

    @Transactional
    public void update(int id, Transaction transaction) {
        transactionRepository.findById(id).ifPresentOrElse(t -> {
            User user = t.getUser();

            userFoundsCancelTransactions(user, t);
            userFundsAddTransactions(user, transaction);

            t.setSum(transaction.getSum());
            t.setDateTime(LocalDateTime.now());
            t.setCategory(transaction.getCategory());
            t.setComment(transaction.getComment());
            t.setRefill(transaction.isRefill());
        }, () -> {
            throw new NoSuchElementException("No transaction with id: " + id);
        });
    }

    @Transactional
    public void delete(int id) {
        transactionRepository.findById(id).ifPresent(t -> {
            userFoundsCancelTransactions(t.getUser(), t);
            transactionRepository.delete(t);
        });
    }

    private void userFundsAddTransactions(User user, Transaction transaction) {
        int oldFunds = user.getFunds();
        if(transaction.isRefill()) {
            user.setFunds(oldFunds + transaction.getSum());
        }else {
            user.setFunds(oldFunds - transaction.getSum());
        }
    }

    private void userFoundsCancelTransactions(User user, Transaction transaction) {
        int oldFunds = user.getFunds();
        if(transaction.isRefill()) {
            user.setFunds(oldFunds - transaction.getSum());
        }else {
            user.setFunds(oldFunds + transaction.getSum());
        }
    }

}
