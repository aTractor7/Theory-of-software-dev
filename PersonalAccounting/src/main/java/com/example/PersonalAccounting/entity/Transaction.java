package com.example.PersonalAccounting.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 0, message = "Transaction sum can't be negative")
    private int sum;

    @Length(max = 150, message = "Comment should be less then 150 characters")
    private String comment;

    private TransactionType transactionType;

    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTime;

    private User user;

    public boolean isEmpty() {
        return sum == 0 || transactionType == null;
    }
}
