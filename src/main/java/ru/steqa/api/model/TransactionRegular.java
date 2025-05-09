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
@Entity(name = "transaction_regulars")
public class TransactionRegular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private TransactionType type;

    @Column(name = "short_name", nullable = false)
    private String shortName;

    @Column(nullable = false)
    @Max(100000000000000000L)
    private Long amount;

    private String description;

    @Column(name = "repetition_rule_id", nullable = false)
    private String repetitionRuleId;

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
    public TransactionRegular(TransactionType type, String shortName, Long amount, String description,
                              String repetitionRuleId, User user, Account account, TransactionCategory category) {
        this.type = type;
        this.shortName = shortName;
        this.amount = amount;
        this.description = description;
        this.repetitionRuleId = repetitionRuleId;
        this.createdAt = ZonedDateTime.now();
        this.user = user;
        this.userId = user.getId();
        this.account = account;
        this.accountId = account.getId();
        this.category = category;
        this.categoryId = category.getId();
    }
}
