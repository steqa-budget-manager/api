package ru.steqa.api.scheme.transaction.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.scheme.validators.ValidEnum;

@Data
public class AddTransactionCategoryScheme {
    @NotNull
    @ValidEnum(enumClass = TransactionType.class)
    private String type;

    @NotNull
    @Length(min = 1, max = 255)
    private String name;

    public TransactionType getType() {
        return TransactionType.valueOf(type);
    }
}
