package ru.steqa.api.scheme.account;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddAccountScheme {
    @NotNull
    @Length(min = 1, max = 255)
    private String name;
}
