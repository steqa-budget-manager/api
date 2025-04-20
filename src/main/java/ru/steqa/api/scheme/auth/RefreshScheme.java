package ru.steqa.api.scheme.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshScheme {
    @NotBlank
    private String refresh;
}
