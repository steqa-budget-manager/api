package ru.steqa.api.scheme.transaction.regular;

import lombok.Builder;
import lombok.Data;
import ru.steqa.api.model.TransactionType;

@Data
public class ResponseTransactionRegularScheme {
    private Long id;
    private TransactionType type;
    private String shortName;
    private Long amount;
    private String description;
    private AddRuleScheme rule;
    private String account;
    private Long accountId;
    private String category;
    private Long categoryId;

    @Builder
    public ResponseTransactionRegularScheme(Long id, TransactionType type, String shortName,
                                            Long amount, String description, AddRuleScheme rule,
                                            String account, Long accountId, String category, Long categoryId) {
        this.id = id;
        this.type = type;
        this.shortName = shortName;
        this.amount = amount;
        this.description = description;
        this.rule = rule;
        this.account = account;
        this.accountId = accountId;
        this.category = category;
        this.categoryId = categoryId;
    }
}
