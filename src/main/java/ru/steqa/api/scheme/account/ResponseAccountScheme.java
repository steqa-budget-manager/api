package ru.steqa.api.scheme.account;

import lombok.Builder;
import lombok.Data;

@Data
public class ResponseAccountScheme {
    private Long id;
    private String name;
    private Boolean visible;

    @Builder
    public ResponseAccountScheme(Long id, String name, Boolean visible) {
        this.id = id;
        this.name = name;
        this.visible = visible;
    }
}
