package ru.steqa.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private TransactionType type;

    @Column(nullable = false)
    private Long amount;

    private String description;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "account_id", insertable = false, updatable = false)
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private TransactionCategory category;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    public Transaction(TransactionType type, Long amount, String description, Date date, Account account, TransactionCategory category) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
        this.accountId = account.getId();
        this.category = category;
        this.categoryId = category.getId();
    }
}
