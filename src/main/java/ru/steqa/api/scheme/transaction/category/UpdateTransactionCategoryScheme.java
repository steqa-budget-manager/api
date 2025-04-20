package ru.steqa.api.scheme.transaction.category;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateTransactionCategoryScheme {
    @Length(min = 1, max = 255)
    private String name;

    private Boolean visible;
}
