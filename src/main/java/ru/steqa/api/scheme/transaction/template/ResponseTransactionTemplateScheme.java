package ru.steqa.api.scheme.transaction.template;

import lombok.Builder;
import lombok.Data;
import ru.steqa.api.model.TransactionType;

import java.time.ZonedDateTime;

@Data
public class ResponseTransactionTemplateScheme {
    private Long id;
    private TransactionType type;
    private Long amount;
    private String description;
    private ZonedDateTime createdAt;
    private String account;
    private Long accountId;
    private String category;
    private Long categoryId;

    @Builder
    public ResponseTransactionTemplateScheme(Long id, TransactionType type, Long amount,
                                             String description, ZonedDateTime createdAt,
                                             String account, Long accountId,
                                             String category, Long categoryId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.account = account;
        this.accountId = accountId;
        this.category = category;
        this.categoryId = categoryId;
    }
}
