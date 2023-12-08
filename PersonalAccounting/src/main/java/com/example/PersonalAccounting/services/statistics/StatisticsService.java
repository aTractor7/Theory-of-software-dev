package com.example.PersonalAccounting.services.statistics;

import com.example.PersonalAccounting.entity.Accumulation;
import com.example.PersonalAccounting.entity.FinancialArrangement;
import com.example.PersonalAccounting.entity.Transaction;
import com.example.PersonalAccounting.entity.User;
import com.example.PersonalAccounting.services.entity_service_impl.AccumulationService;
import com.example.PersonalAccounting.services.entity_service_impl.FinancialArrangementService;
import com.example.PersonalAccounting.services.entity_service_impl.TransactionService;
import com.example.PersonalAccounting.util.exceptions.FileDeleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
public class StatisticsService {

    private final TransactionService transactionService;
    private final AccumulationService accumulationService;
    private final FinancialArrangementService financialArrangementService;

    private StatisticsFileHandler fileHandler;

    @Autowired
    public StatisticsService(TransactionService transactionService, AccumulationService accumulationService,
                             FinancialArrangementService financialArrangementService) {
        this.transactionService = transactionService;
        this.accumulationService = accumulationService;
        this.financialArrangementService = financialArrangementService;
    }

    @Transactional(readOnly = true)
    public File getStatisticsInFile(User user) {
        if(fileHandler == null)
            throw new NullPointerException("Can't generate statistics file without fileGenerator");

        List<Transaction> transactions = transactionService.getAll(user);
        List<Accumulation> accumulations = accumulationService.getAll(user);
        List<FinancialArrangement> arrangements = financialArrangementService.getAll(user);

        return fileHandler.generateStatisticsFile(transactions, accumulations, arrangements, user.getEmail());
    }

    public void deleteUploadedFile(File file) {
        if(!fileHandler.deleteFile(file)) {
            throw new FileDeleteException("File not deleting. Path" + file.getAbsolutePath());
        }
    }

    public StatisticsFileHandler getFileGenerator() {
        return fileHandler;
    }

    public void setFileGenerator(StatisticsFileHandler fileGenerator) {
        this.fileHandler = fileGenerator;
    }
}
