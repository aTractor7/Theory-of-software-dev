package com.example.dbmicroservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatisticFileRequest {

    @NotNull
    private List<TransactionDTO> transactionDTOList;
    @NotNull
    private List<FinancialArrangementDTO> financialArrangementDTOList;
    @NotNull
    private List<AccumulationDTO> accumulationDTOList;
    @Email
    private String email;

    @NotBlank
    private String format;
}
