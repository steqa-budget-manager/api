package ru.steqa.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "transaction_templates")
public class TransactionTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private TransactionType type;

    @Column(nullable = false)
    @Max(100000000000000000L)
    private Long amount;

    private String description;

    @Column(name = "created_at", nullable = false)
    @ColumnDefault("now()")
    private ZonedDateTime createdAt = ZonedDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "account_id", nullable = false, insertable = false, updatable = false)
    private Long accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private TransactionCategory category;

    @Column(name = "category_id", nullable = false, insertable = false, updatable = false)
    private Long categoryId;

    @Builder
    public TransactionTemplate(TransactionType type, Long amount, String description,
                               User user, Account account, TransactionCategory category) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = ZonedDateTime.now();
        this.user = user;
        this.userId = user.getId();
        this.account = account;
        this.accountId = account.getId();
        this.category = category;
        this.categoryId = category.getId();
    }
}
