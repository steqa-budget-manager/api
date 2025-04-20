package ru.steqa.api.scheme.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginScheme {
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 255)
    private String password;
}
