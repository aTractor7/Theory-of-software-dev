package com.example.PersonalAccounting.entity;

import com.example.PersonalAccounting.entity.enums.TransactionCategory;

import java.util.HashMap;
import java.util.Map;

public class TransactionTypeFactory {

    static Map<String,TransactionType> typeMap = new HashMap<>();

    public static TransactionType getTransactionType(TransactionCategory category, boolean isRefill) {
        TransactionType result = typeMap.get(category.toString());
        if(result == null) {
            result = new TransactionType(category, isRefill);
            typeMap.put(category.toString(), result);
        }
        return result;
    }
}
