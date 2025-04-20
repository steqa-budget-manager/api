package ru.steqa.api.schema.transaction;

import lombok.Builder;
import lombok.Data;
import ru.steqa.api.model.TransactionType;

import java.time.LocalDate;

@Data
public class ResponseTransactionSchema {
    private Long id;
    private TransactionType type;
    private Long amount;
    private String description;
    private LocalDate date;
    private Long accountId;
    private Long categoryId;

    @Builder
    public ResponseTransactionSchema(Long id, TransactionType type, Long amount, String description,
                                     LocalDate date, Long accountId, Long categoryId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }
}
