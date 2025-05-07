package ru.steqa.api.scheme.transaction.category;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.scheme.validators.ValidEnum;

@Data
public class UpdateTransactionCategoryScheme {
    @ValidEnum(enumClass = TransactionType.class)
    private String type;

    @Length(min = 1, max = 255)
    private String name;

    private Boolean visible;

    public TransactionType getType() {
        if (type == null) return null;
        return TransactionType.valueOf(type);
    }
}
