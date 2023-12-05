package com.example.PersonalAccounting.controllers;


import com.example.PersonalAccounting.entity.Accumulation;
import com.example.PersonalAccounting.entity.FinancialArrangement;
import com.example.PersonalAccounting.entity.Transaction;
import com.example.PersonalAccounting.entity.User;
import com.example.PersonalAccounting.entity.enums.FinancialArrangementState;
import com.example.PersonalAccounting.entity.enums.Status;
import com.example.PersonalAccounting.entity.enums.TransactionCategory;
import com.example.PersonalAccounting.services.crud_seervice_impl.*;
import com.example.PersonalAccounting.services.statistics.ExcelStatisticsFileGenerator;
import com.example.PersonalAccounting.services.statistics.StatisticsService;
import com.example.PersonalAccounting.services.statistics.WordStatisticsFileGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.time.LocalDate;

@RestController
@RequestMapping("/test")
public class TestController {

    private final UserService userService;
    private final TransactionService transactionService;
    private final AccumulationService accumulationService;
    private final FinancialArrangementService financialArrangementService;
    private final AbstractFinancialArrangementServiceDecorator financialArrangementServiceUserFoundsDecorator;
    private final StatisticsService statisticsService;

    @Autowired
    public TestController(UserService userService, TransactionService transactionService,
                          AccumulationService accumulationService, FinancialArrangementService financialArrangementService,
                          AbstractFinancialArrangementServiceDecorator financialArrangementServiceDecorator, StatisticsService statisticsService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.accumulationService = accumulationService;
        this.financialArrangementService = financialArrangementService;
        this.financialArrangementServiceUserFoundsDecorator = financialArrangementServiceDecorator;
        this.statisticsService = statisticsService;
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
        deposit.setEndDate(LocalDate.of(2025, 11, 28));
        deposit.setState(FinancialArrangementState.DEPOSIT);

        FinancialArrangement credit = new FinancialArrangement();
        credit.setName("Credit");
        credit.setStartSum(10_000);
        credit.setPercent(10);
        credit.setFromToUserFunds(true);
        credit.setEndDate(LocalDate.of(2024, 3, 30));
        credit.setState(FinancialArrangementState.CREDIT);



        User currentUser = userService.getOne(userDetails.getUsername());
        deposit.setUser(currentUser);



        User newUser = new User();
        newUser.setName("Petya");
        newUser.setEmail("petya@email.com");
        newUser.setFunds(1_000_000);

        Transaction newTransaction = new Transaction();
        newTransaction.setComment("Updated");
        newTransaction.setSum(1);
        newTransaction.setRefill(true);
        newTransaction.setCategory(TransactionCategory.DWELLING);

        FinancialArrangement newFA = new FinancialArrangement();
        newFA.setName("Updated");
        newFA.setState(FinancialArrangementState.CREDIT);
        newFA.setPercent(100);
        newFA.setFromToUserFunds(false);
        newFA.setStartSum(100);
        newFA.setEndDate(LocalDate.of(2025, 10, 10));
        newFA.setStatus(Status.OVERDUE);

        Accumulation newAccumulation = new Accumulation();
        newAccumulation.setName("Updated");
        newAccumulation.setComment("I was updated");
        newAccumulation.setStatus(Status.OVERDUE);
        newAccumulation.setGoalSum(100);
        newAccumulation.setEndDate(LocalDate.of(2025, 10, 10));

        statisticsService.setFileGenerator(new ExcelStatisticsFileGenerator());
        File file = statisticsService.getStatisticsInFile(currentUser);
        statisticsService.deleteUploadedFile(file);

        return "ok";
    }
}
