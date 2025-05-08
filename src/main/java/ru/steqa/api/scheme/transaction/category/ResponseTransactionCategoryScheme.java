package ru.steqa.api.scheme.transaction.category;

import lombok.Builder;
import lombok.Data;
import ru.steqa.api.model.TransactionType;

import java.time.ZonedDateTime;

@Data
public class ResponseTransactionCategoryScheme {
    private Long id;
    private TransactionType type;
    private String name;
    private Boolean visible;
    private ZonedDateTime createdAt;

    @Builder
    public ResponseTransactionCategoryScheme(
            Long id,
            TransactionType type,
            String name,
            Boolean visible,
            ZonedDateTime createdAt
    ) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.visible = visible;
        this.createdAt = createdAt;
    }
}
