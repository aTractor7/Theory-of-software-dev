package com.example.PersonalAccounting.repositories;

import com.example.PersonalAccounting.entity.Transaction;
import com.example.PersonalAccounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUser(User user);
}
