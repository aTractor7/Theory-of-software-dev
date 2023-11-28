package com.example.PersonalAccounting.entity;

import com.example.PersonalAccounting.entity.enums.TransactionCategory;
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
@Entity
@Table(name = "transaction")
public class Transaction implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Min(value = 0, message = "Transaction sum can't be negative")
    @Column(name = "sum")
    private int sum;

    @Length(max = 150, message = "Comment should be less then 150 characters")
    @Column(name = "comment")
    private String comment;

    @NotNull(message = "Category can't be empty")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "category")
    private TransactionCategory category;

    @Column(name = "refill")
    private boolean refill;

    @PastOrPresent
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public boolean isEmpty() {
        return sum == 0 || category == null;
    }

    @Override
    public Transaction clone() {

        try {
            Transaction clone = (Transaction) super.clone();
            clone.setSum(sum);
            clone.setDateTime(dateTime);
            clone.setCategory(category);
            clone.setRefill(refill);
            clone.setComment(comment);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}
