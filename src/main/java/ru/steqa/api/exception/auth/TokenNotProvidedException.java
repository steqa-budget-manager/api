package ru.steqa.api.exception.auth;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class TokenNotProvidedException extends HttpException {
    public TokenNotProvidedException() {
        super(HttpStatus.FORBIDDEN, "Token is not provided");
    }
}
