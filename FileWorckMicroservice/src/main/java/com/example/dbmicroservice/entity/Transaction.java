package com.example.dbmicroservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction implements Cloneable {

    private int id;

    @Min(value = 0, message = "Transaction sum can't be negative")
    private int sum;

    @Length(max = 150, message = "Comment should be less then 150 characters")
    private String comment;

    @NotNull(message = "Category can't be empty")
    private TransactionCategory category;

    @Column(name = "refill")
    private boolean refill;

    private LocalDateTime dateTime;

    @Column(name = "periodic")
    private boolean periodic;


    public boolean isEmpty() {
        return sum == 0 || category == null;
    }
}
