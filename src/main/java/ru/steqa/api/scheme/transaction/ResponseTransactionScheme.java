package ru.steqa.api.scheme.transaction;

import lombok.Builder;
import lombok.Data;
import ru.steqa.api.model.TransactionType;

import java.time.ZonedDateTime;

@Data
public class ResponseTransactionScheme {
    private Long id;
    private TransactionType type;
    private Long amount;
    private String description;
    private ZonedDateTime date;
    private String account;
    private Long accountId;
    private String category;
    private Long categoryId;

    @Builder
    public ResponseTransactionScheme(Long id, TransactionType type, Long amount, String description,
                                     ZonedDateTime date, String account, Long accountId, String category, Long categoryId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
        this.accountId = accountId;
        this.category = category;
        this.categoryId = categoryId;
    }
}
