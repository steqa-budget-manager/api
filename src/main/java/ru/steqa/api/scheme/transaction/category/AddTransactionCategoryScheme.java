package ru.steqa.api.scheme.transaction.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddTransactionCategoryScheme {
    @NotNull
    @Length(min = 1, max = 255)
    private String name;
}
