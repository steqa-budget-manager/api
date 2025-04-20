package ru.steqa.api.exception.auth;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class TokenInvalidException extends HttpException {
    public TokenInvalidException() {
        super(HttpStatus.FORBIDDEN, "Token is invalid");
    }
}
