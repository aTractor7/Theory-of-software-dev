package com.example.PersonalAccounting.services.finantial_arrangement_calculations;

import com.example.PersonalAccounting.model.FinancialArrangement;
import com.example.PersonalAccounting.model.enums.FinancialArrangementState;

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
}
