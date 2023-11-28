package com.example.PersonalAccounting.controllers;


import com.example.PersonalAccounting.entity.Accumulation;
import com.example.PersonalAccounting.entity.FinancialArrangement;
import com.example.PersonalAccounting.entity.Transaction;
import com.example.PersonalAccounting.entity.User;
import com.example.PersonalAccounting.entity.enums.FinancialArrangementState;
import com.example.PersonalAccounting.entity.enums.TransactionCategory;
import com.example.PersonalAccounting.repositories.TransactionRepository;
import com.example.PersonalAccounting.services.crud_seervice_impl.AccumulationService;
import com.example.PersonalAccounting.services.crud_seervice_impl.FinancialArrangementService;
import com.example.PersonalAccounting.services.crud_seervice_impl.TransactionService;
import com.example.PersonalAccounting.services.crud_seervice_impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/test")
public class TestController {

    private final UserService userService;
    private final TransactionService transactionService;
    private final AccumulationService accumulationService;
    private final FinancialArrangementService financialArrangementService;

    @Autowired
    public TestController(UserService userService, TransactionService transactionService,
                          AccumulationService accumulationService, FinancialArrangementService financialArrangementService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.accumulationService = accumulationService;
        this.financialArrangementService = financialArrangementService;
    }

    @GetMapping
    public String test(@AuthenticationPrincipal UserDetails userDetails) {

        Transaction transaction = new Transaction();
        transaction.setSum(250);
        transaction.setRefill(false);
        transaction.setCategory(TransactionCategory.OTHER);

        Accumulation accumulation = new Accumulation();
        accumulation.setName("Phone");
        accumulation.setGoalSum(1000);
        accumulation.setEndDate(LocalDate.of(2030, 12, 31));
        accumulation.setComment("I to listen music");

        FinancialArrangement deposit = new FinancialArrangement();
        deposit.setName("Deposit");
        deposit.setStartSum(10_000);
        deposit.setPercent(5);
        deposit.setFromToUserFunds(true);
        deposit.setEndDate(LocalDate.of(2024, 11, 28));
        deposit.setState(FinancialArrangementState.DEPOSIT);

        FinancialArrangement credit = new FinancialArrangement();
        credit.setName("Credit5");
        credit.setStartSum(10_000);
        credit.setPercent(10);
        credit.setFromToUserFunds(true);
        credit.setEndDate(LocalDate.of(2024, 3, 30));
        credit.setState(FinancialArrangementState.CREDIT);



        User currentUser = userService.getOne(userDetails.getUsername());

        financialArrangementService.makePayment(15);

        return "ok";
    }
}
