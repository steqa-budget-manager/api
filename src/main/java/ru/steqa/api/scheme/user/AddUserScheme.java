package ru.steqa.api.scheme.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddUserScheme {
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @NotNull
    @Size(min = 5, max = 255)
    private String password;
}
