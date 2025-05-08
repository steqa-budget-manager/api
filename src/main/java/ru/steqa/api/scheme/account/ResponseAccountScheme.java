package ru.steqa.api.scheme.account;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ResponseAccountScheme {
    private Long id;
    private String name;
    private Boolean visible;
    private ZonedDateTime createdAt;

    @Builder
    public ResponseAccountScheme(Long id, String name, Boolean visible, ZonedDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.visible = visible;
        this.createdAt = createdAt;
    }
}
