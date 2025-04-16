package ru.steqa.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpException extends RuntimeException implements IHttpException {
    private final HttpStatus status;
    private final String code;

    public HttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.code = status.getReasonPhrase();
    }
}
