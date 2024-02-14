package com.example.PersonalAccounting.services.finantial_arrangement_calculations;

import com.example.PersonalAccounting.entity.FinancialArrangement;
import com.example.PersonalAccounting.entity.Transaction;
import com.example.PersonalAccounting.entity.User;
import com.example.PersonalAccounting.entity.enums.FinancialArrangementState;

import java.time.LocalDate;

public interface FinancialArrangementCalculations {

    void makePayment(FinancialArrangement arrangement);

    int calculateCurrentSumInitValue(FinancialArrangement arrangement);

    int calculateRefundSum(FinancialArrangement arrangement);

    int calculatePaymentSumByTimeLine(FinancialArrangement arrangement);

    boolean isOutOfDate(FinancialArrangement arrangement);

    boolean isFullyRepaid(FinancialArrangement arrangement);

    int timeLineBetweenDates(LocalDate start, LocalDate end);

    FinancialArrangementState operatedState();

    Transaction createPaymentTransaction(FinancialArrangement arrangement, User user);

    Transaction createStartTransaction(FinancialArrangement financialArrangement, User user);

    Transaction createEndTransaction(FinancialArrangement financialArrangement, User user);
}
