package ru.steqa.api.scheme.transaction.category;

import lombok.Data;
import ru.steqa.api.model.TransactionType;

@Data
public class TransactionCategoryFilter {
    private TransactionType type;
    private Boolean visible;
}
