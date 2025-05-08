package ru.steqa.api.scheme.user;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ResponseUserScheme {
    private Long id;
    private String email;
    private String password;
    private ZonedDateTime createdAt;

    @Builder
    public ResponseUserScheme(Long id, String email, String password, ZonedDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }
}
