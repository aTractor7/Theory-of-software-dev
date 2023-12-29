package com.example.dbmicroservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FinancialArrangement {

    private int id;

    @NotBlank
    @Length(min = 1, max = 50, message = "Name should contains less than 50 characters")
    private String name;

    @Min(value = 0, message = "Rate should be greater then 0")
    private int percent;

    @Min(value = 0, message = "Current sum should be greater then 0")
    private int startSum;

    @Min(value = 0, message = "Current sum should be greater then 0")
    private int currentSum;

    @Transient
    private int refundSum;

    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    private LocalDate endDate;

    @Column(name = "from_to_user_funds")
    private boolean fromToUserFunds;


    private FinancialArrangementState state;

    @NotNull
    private Status status;
}
