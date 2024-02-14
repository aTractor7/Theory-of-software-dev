package com.example.PersonalAccounting.aop;

import org.aspectj.lang.annotation.Pointcut;

public class ServicePointcuts {

    @Pointcut("execution(* com.example.PersonalAccounting.services.CrudService.*(..)) ||" +
            "execution(* com.example.PersonalAccounting.services.crud_seervice_impl.AccumulationService.closeAccumulation(..)) ||" +
            "execution(* com.example.PersonalAccounting.services.crud_seervice_impl.AccumulationService.makePayment(..)) ||" +
            "execution(* com.example.PersonalAccounting.services.crud_seervice_impl.FinancialArrangementService.makePayment(..))")
    public void crudMethods() {}

    @Pointcut("execution(* com.example.PersonalAccounting.services.crud_seervice_impl.AccumulationService.closeAccumulation(..)) ||" +
            "execution(* com.example.PersonalAccounting.services.crud_seervice_impl.AccumulationService.makePayment(..)) ||" +
            "execution(* com.example.PersonalAccounting.services.crud_seervice_impl.FinancialArrangementService.makePayment(..))")
    public void paymentMethods() {}

    @Pointcut("execution(* com.example.PersonalAccounting.services.statistics.StatisticsFileGenerator.*(..))")
    public void statisticsMethods() {}

}
