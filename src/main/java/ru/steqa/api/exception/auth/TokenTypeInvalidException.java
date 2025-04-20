package ru.steqa.api.exception.auth;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class TokenTypeInvalidException extends HttpException {
    public TokenTypeInvalidException() {
        super(HttpStatus.FORBIDDEN, "Token type is invalid");
    }

    public TokenTypeInvalidException(String received, String expected) {
        super(HttpStatus.FORBIDDEN, String.format("Token type is invalid: received '%s', expected '%s'", received, expected));
    }
}
