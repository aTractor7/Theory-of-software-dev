package com.example.PersonalAccounting.repositories;

import com.example.PersonalAccounting.entity.FinancialArrangement;
import com.example.PersonalAccounting.entity.Transaction;
import com.example.PersonalAccounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FinancialArrangementRepository extends JpaRepository<FinancialArrangement, Integer> {

    List<FinancialArrangement> findByUser(User user);
}
