package com.example.PersonalAccounting.dao.row_mapper;

import com.example.PersonalAccounting.entity.Transaction;
import com.example.PersonalAccounting.entity.TransactionType;
import com.example.PersonalAccounting.entity.TransactionTypeFactory;
import com.example.PersonalAccounting.entity.User;
import com.example.PersonalAccounting.entity.enums.TransactionCategory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class TransactionRowMapper implements RowMapper<Transaction> {

    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction transaction = new Transaction();

        TransactionCategory category = TransactionCategory.getCategoryByName(rs.getString("category"));
        TransactionType type = TransactionTypeFactory
                .getTransactionType(category, rs.getBoolean("refill"));

        transaction.setId(rs.getInt("id"));
        transaction.setSum(rs.getInt("sum"));
        transaction.setComment(rs.getString("comment"));
        transaction.setTransactionType(type);
        transaction.setDateTime(LocalDateTime.parse(rs.getString("date_time")));
        transaction.setUser(new User(rs.getInt("user_id")));
        return null;
    }
}
