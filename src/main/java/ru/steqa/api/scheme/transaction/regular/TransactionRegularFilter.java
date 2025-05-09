package ru.steqa.api.scheme.transaction.regular;

import lombok.Data;
import ru.steqa.api.model.TransactionType;

@Data
public class TransactionRegularFilter {
    private TransactionType type;
}
