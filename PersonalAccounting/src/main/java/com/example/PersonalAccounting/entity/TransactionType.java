package com.example.PersonalAccounting.entity;

import com.example.PersonalAccounting.entity.enums.TransactionCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionType {

    @NotNull(message = "Category can't be empty")
    @Enumerated(value = EnumType.STRING)
    private TransactionCategory category;

    private boolean refill;
}
