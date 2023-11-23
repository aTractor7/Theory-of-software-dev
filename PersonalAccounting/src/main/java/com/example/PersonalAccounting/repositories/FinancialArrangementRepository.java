package com.example.PersonalAccounting.repositories;

import com.example.PersonalAccounting.model.FinancialArrangement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinancialArrangementRepository extends JpaRepository<FinancialArrangement, Integer> {

    Optional<FinancialArrangement> findByNameAndUser_Id(String name, int userId);
}
