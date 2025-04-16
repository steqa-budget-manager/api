package ru.steqa.api.schema.transaction;

import lombok.Data;
import ru.steqa.api.model.TransactionType;

import java.util.Date;

@Data
public class AddTransactionSchema {
    private TransactionType type;
    private Long amount;
    private String description;
    private Date date;
    private Long accountId;
    private Long categoryId;
}
