package com.example.PersonalAccounting.services.finantial_arrangement_calculations.impl;

import com.example.PersonalAccounting.model.FinancialArrangement;
import com.example.PersonalAccounting.model.enums.FinancialArrangementState;
import com.example.PersonalAccounting.services.finantial_arrangement_calculations.FinancialArrangementCalculations;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class CreditCalculations implements FinancialArrangementCalculations {

    @Override
    public void makePayment(FinancialArrangement arrangement) {
        arrangement.setCurrentSum(arrangement.getCurrentSum() - calculatePaymentSumByTimeLine(arrangement));
    }

    @Override
    public int calculateCurrentSumInitValue(FinancialArrangement arrangement) {
        return calculateRefundSum(arrangement);
    }

    @Override
    public int calculateRefundSum(FinancialArrangement arrangement) {
        int overpayment = arrangement.getStartSum() * arrangement.getPercent() / 100;
        return arrangement.getStartSum() + Math.round(overpayment);
    }

    @Override
    public int calculatePaymentSumByTimeLine(FinancialArrangement arrangement) {
        int paymentsNum = timeLineBetweenDates(
                arrangement.getStartDate(), arrangement.getEndDate());
        return (calculateRefundSum(arrangement) - arrangement.getStartSum()) / paymentsNum;
    }

    @Override
    public boolean isOutOfDate(FinancialArrangement arrangement) {
        int requirePayments = timeLineBetweenDates(arrangement.getStartDate(), LocalDate.now());
        if(requirePayments == 0) return false;

        int paidOut = calculateRefundSum(arrangement) - arrangement.getCurrentSum();
        int madePayments = paidOut / calculatePaymentSumByTimeLine(arrangement);

        return !(madePayments >= requirePayments);
    }

    @Override
    public boolean isFullyRepaid(FinancialArrangement arrangement) {
        return arrangement.getCurrentSum() == 0;
    }

    @Override
    public int timeLineBetweenDates(LocalDate start, LocalDate end) {
        return (int) ChronoUnit.MONTHS.between(start, end);
    }

    @Override
    public FinancialArrangementState operatedState() {
        return FinancialArrangementState.CREDIT;
    }


}
