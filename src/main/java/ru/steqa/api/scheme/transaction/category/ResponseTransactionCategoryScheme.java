package ru.steqa.api.scheme.transaction.category;

import lombok.Builder;
import lombok.Data;

@Data
public class ResponseTransactionCategoryScheme {
    private Long id;
    private String name;
    private Boolean visible;

    @Builder
    public ResponseTransactionCategoryScheme(Long id, String name, Boolean visible) {
        this.id = id;
        this.name = name;
        this.visible = visible;
    }
}
