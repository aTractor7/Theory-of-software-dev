package com.example.PersonalAccounting.repositories;

import com.example.PersonalAccounting.entity.Accumulation;
import com.example.PersonalAccounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccumulationRepository extends JpaRepository<Accumulation, Integer> {

    List<Accumulation> findByUser(User user);
}
