package ru.steqa.api.schema.user;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseUserScheme {
    private Long id;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    @Builder
    public ResponseUserScheme(Long id, String email, String password, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }
}
