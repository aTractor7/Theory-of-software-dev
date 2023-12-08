package com.example.PersonalAccounting.dao;

import com.example.PersonalAccounting.dao.row_mapper.TransactionRowMapper;
import com.example.PersonalAccounting.entity.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    private final TransactionRowMapper rowMapper;

    public TransactionDao(JdbcTemplate jdbcTemplate, TransactionRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }


    public List<Transaction> index() {
        return jdbcTemplate.query("SELECT * FROM transaction", rowMapper);
    }

    public Transaction show(int id){
        return jdbcTemplate.query("SELECT * FROM transaction WHERE id=?", rowMapper, id)
                .stream().findAny().orElse(null);
    }

    public void save(Transaction transaction) {
        jdbcTemplate.update(
                "INSERT INTO transaction(sum, personal_accounting.transaction.comment, refill, date_time, user_id, category)" +
                        " VALUES (?,?,?,?,?,?)",
                transaction.getSum(), transaction.getComment(), transaction.getDateTime(),
                transaction.getTransactionType().getCategory(), transaction.getTransactionType().isRefill(),
                transaction.getUser().getId());
    }

    public void update(int id, Transaction transaction) {
        jdbcTemplate.update("UPDATE transaction SET sum=?, personal_accounting.transaction.comment=?, refill=?," +
                        " date_time=?, user_id=?, category=? WHERE id=?",
                transaction.getSum(), transaction.getComment(), transaction.getDateTime(),
                transaction.getTransactionType().getCategory(), transaction.getTransactionType().isRefill(),
                transaction.getUser().getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM personal_accounting.transaction WHERE id=?", id);
    }
}
