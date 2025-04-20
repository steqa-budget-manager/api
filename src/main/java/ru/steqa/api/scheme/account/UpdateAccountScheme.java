package ru.steqa.api.scheme.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateAccountScheme {
    @Length(min = 1, max = 255)
    private String name;

    private Boolean visible;
}
