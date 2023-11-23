package com.example.PersonalAccounting.controllers;


import com.example.PersonalAccounting.services.AccumulationService;
import com.example.PersonalAccounting.services.FinancialArrangementService;
import com.example.PersonalAccounting.services.TransactionService;
import com.example.PersonalAccounting.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

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
        System.out.println(userDetails);
        return "ok";
    }
}
