package com.example.PersonalAccounting.services.entity_service_impl;

import com.example.PersonalAccounting.entity.FinancialArrangement;
import com.example.PersonalAccounting.services.CrudService;

import java.util.List;

public abstract class AbstractFinancialArrangementServiceDecorator implements CrudService<FinancialArrangement> {

    //TODO: change next field to CrudService<FA> for multiple decorator
    protected final FinancialArrangementService financialArrangementService;

    public AbstractFinancialArrangementServiceDecorator(FinancialArrangementService financialArrangementService) {
        this.financialArrangementService = financialArrangementService;
    }


    @Override
    public FinancialArrangement create(FinancialArrangement toCreate) {
        return financialArrangementService.create(toCreate);
    }

    @Override
    public List<FinancialArrangement> getAll() {
        return financialArrangementService.getAll();
    }

    @Override
    public FinancialArrangement getOne(int id) {
        return financialArrangementService.getOne(id);
    }

    @Override
    public FinancialArrangement update(int id, FinancialArrangement updated) {
        return financialArrangementService.update(id, updated);
    }

    @Override
    public void delete(int id) {
        financialArrangementService.delete(id);
    }


    public FinancialArrangement makePayment(int id) {return financialArrangementService.makePayment(id);}
}
