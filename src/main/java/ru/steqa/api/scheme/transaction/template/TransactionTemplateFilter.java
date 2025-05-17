package ru.steqa.api.scheme.transaction.template;

import lombok.Data;
import ru.steqa.api.model.TransactionType;

@Data
public class TransactionTemplateFilter {
    private TransactionType type;
}
