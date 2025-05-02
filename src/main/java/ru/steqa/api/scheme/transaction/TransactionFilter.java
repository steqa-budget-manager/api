package ru.steqa.api.scheme.transaction;

import lombok.Data;
import ru.steqa.api.model.TransactionType;

@Data
public class TransactionFilter {
    private TransactionType type;
}
