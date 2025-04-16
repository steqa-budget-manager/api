package ru.steqa.api.schema.transaction;

import lombok.Data;
import ru.steqa.api.model.TransactionType;

import java.util.Date;

@Data
public class ResponseTransactionSchema {
    private Long id;
    private TransactionType type;
    private Long amount;
    private String description;
    private Date date;
    private Long accountId;
    private Long categoryId;

    public ResponseTransactionSchema(Long id, TransactionType type, Long amount,
                                     String description, Date date, Long accountId, Long categoryId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }
}
