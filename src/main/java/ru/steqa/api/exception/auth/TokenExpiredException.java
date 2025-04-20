package ru.steqa.api.exception.auth;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class TokenExpiredException extends HttpException {
    public TokenExpiredException() {
        super(HttpStatus.FORBIDDEN, "Token has expired");
    }
}
