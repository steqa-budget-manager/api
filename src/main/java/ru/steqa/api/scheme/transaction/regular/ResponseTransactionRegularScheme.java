package ru.steqa.api.scheme.transaction.regular;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.steqa.api.model.TransactionType;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseTransactionRegularScheme {
    private Long id;
    private TransactionType type;
    private String shortName;
    private Long amount;
    private String description;
    private AddRuleScheme rule;
    private ZonedDateTime createdAt;
    private String account;
    private Long accountId;
    private String category;
    private Long categoryId;

    @Builder
    public ResponseTransactionRegularScheme(Long id, TransactionType type, String shortName,
                                            Long amount, String description, AddRuleScheme rule, ZonedDateTime createdAt,
                                            String account, Long accountId, String category, Long categoryId) {
        this.id = id;
        this.type = type;
        this.shortName = shortName;
        this.amount = amount;
        this.description = description;
        this.rule = rule;
        this.createdAt = createdAt;
        this.account = account;
        this.accountId = accountId;
        this.category = category;
        this.categoryId = categoryId;
    }
}
