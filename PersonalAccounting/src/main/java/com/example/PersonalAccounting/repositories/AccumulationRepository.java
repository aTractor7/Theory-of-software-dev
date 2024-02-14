package com.example.PersonalAccounting.repositories;

import com.example.PersonalAccounting.model.Accumulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccumulationRepository extends JpaRepository<Accumulation, Integer> {
}
