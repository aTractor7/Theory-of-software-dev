package com.example.dbmicroservice.util.dto_entity_converter;



import com.example.dbmicroservice.dto.TransactionDTO;
import com.example.dbmicroservice.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionDTOConverter {

    private final ModelMapper modelMapper;

    public TransactionDTOConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Transaction convertToTransaction(TransactionDTO transactionDTO) {
        return modelMapper.map(transactionDTO, Transaction.class);}

    public TransactionDTO convertToTransactionDTO(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);}
}
