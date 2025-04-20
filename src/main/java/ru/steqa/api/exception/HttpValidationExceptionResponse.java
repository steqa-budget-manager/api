package ru.steqa.api.exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonPropertyOrder({ "status", "code", "message", "details", "path", "timestamp" })
public class HttpValidationExceptionResponse extends HttpExceptionResponse {
    private Map<String, List<String>> details;

    public HttpValidationExceptionResponse(HttpStatus status, String message, String path, Map<String, List<String>> details) {
        super(status, message, path);
        this.details = details;
    }
}
