package com.example.dbmicroservice.controller;

import com.example.dbmicroservice.dto.StatisticFileRequest;
import com.example.dbmicroservice.entity.Accumulation;
import com.example.dbmicroservice.entity.FinancialArrangement;
import com.example.dbmicroservice.entity.Transaction;
import com.example.dbmicroservice.service.StatisticsService;
import com.example.dbmicroservice.util.dto_entity_converter.AccumulationDtoConverter;
import com.example.dbmicroservice.util.dto_entity_converter.FinancialArrangementDtoConverter;
import com.example.dbmicroservice.util.dto_entity_converter.TransactionDTOConverter;
import com.example.dbmicroservice.util.responce.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class StatisticFileController {

    private final StatisticsService statisticsService;

    private final AccumulationDtoConverter accumulationDtoConverter;
    private final FinancialArrangementDtoConverter financialArrangementDtoConverter;
    private final TransactionDTOConverter transactionDTOConverter;

    @Autowired
    public StatisticFileController(StatisticsService statisticsService, AccumulationDtoConverter accumulationDtoConverter, FinancialArrangementDtoConverter financialArrangementDtoConverter, TransactionDTOConverter transactionDTOConverter) {
        this.statisticsService = statisticsService;
        this.accumulationDtoConverter = accumulationDtoConverter;
        this.financialArrangementDtoConverter = financialArrangementDtoConverter;
        this.transactionDTOConverter = transactionDTOConverter;
    }

    @GetMapping
    public ResponseEntity<Resource> statisticsFile(@RequestBody StatisticFileRequest statisticFileRequest) {

        List<Transaction> transactions = statisticFileRequest.getTransactionDTOList().stream()
                .map(transactionDTOConverter::convertToTransaction).toList();
        List<Accumulation> accumulations = statisticFileRequest.getAccumulationDTOList().stream()
                .map(accumulationDtoConverter::convertToAccumulation).toList();
        List<FinancialArrangement> faList = statisticFileRequest.getFinancialArrangementDTOList().stream()
                .map(financialArrangementDtoConverter::convertToFA).toList();


        File file = statisticsService.getStatisticsInFile(transactions, accumulations, faList, statisticFileRequest.getEmail());

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Statistics."
                    + statisticFileRequest.getFormat());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("Something went wrong. Try later.");
        }
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
