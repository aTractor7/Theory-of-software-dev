package com.example.dbmicroservice.service;

import com.example.dbmicroservice.entity.Accumulation;
import com.example.dbmicroservice.entity.FinancialArrangement;
import com.example.dbmicroservice.entity.Transaction;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class StatisticsService {


    //TODO: maybe set default value for handler
    private StatisticsFileHandler fileHandler;


    @Transactional(readOnly = true)
    public File getStatisticsInFile(List<Transaction> transactions, List<Accumulation> accumulations,
                                     List<FinancialArrangement> arrangements, String email) {
        if(fileHandler == null)
            throw new NullPointerException("Can't generate statistics file without fileGenerator");

        if(transactions.isEmpty() || accumulations.isEmpty() || arrangements.isEmpty())
            throw new IllegalArgumentException("No data for statistics");

        return fileHandler.generateStatisticsFile(transactions, accumulations, arrangements, email);
    }

    @Scheduled(cron = "${statistics.file.cron.daily}")
    public void deleteDailyStatisticsFile() throws IOException {
        StatisticsFileHandler.deleteAllFile();
    }

    public StatisticsFileHandler getFileHandler() {
        return fileHandler;
    }

    public void setFileHandler(StatisticsFileHandler fileGenerator) {
        this.fileHandler = fileGenerator;
    }
}
