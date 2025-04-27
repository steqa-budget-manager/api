package ru.steqa.api.scheme.transaction.regular;

import lombok.Builder;
import lombok.Data;
import ru.steqa.api.model.TransactionType;

@Data
public class ResponseTransactionRegularScheme {
    private Long id;
    private TransactionType type;
    private Long amount;
    private String description;
    private AddRuleScheme rule;
    private Long accountId;
    private Long categoryId;

    @Builder
    public ResponseTransactionRegularScheme(Long id, TransactionType type, Long amount, String description,
                                            AddRuleScheme rule, Long accountId, Long categoryId) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.rule = rule;
        this.accountId = accountId;
        this.categoryId = categoryId;
    }
}
