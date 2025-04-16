package ru.steqa.api.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonPropertyOrder({ "status", "code", "message", "path", "timestamp" })
public class HttpExceptionResponse {
    private int status;
    private String code;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public HttpExceptionResponse(HttpStatus status, String message, String path) {
        this.status = status.value();
        this.code = status.name();
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
