package ru.steqa.api.scheme.transaction.template;

import lombok.Builder;
import lombok.Data;
import ru.steqa.api.model.TransactionType;

@Data
public class ResponseTransactionTemplateScheme {
    private Long id;
    private TransactionType type;
    private Long amount;
    private String description;
    private Long accountId;
    private Long categoryId;

    @Builder
    public ResponseTransactionTemplateScheme(Long id, TransactionType type, Long amount,
                                             String description, Long accountId, Long categoryId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }
}
