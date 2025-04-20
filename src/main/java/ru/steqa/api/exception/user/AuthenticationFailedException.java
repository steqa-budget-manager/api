package ru.steqa.api.exception.user;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class AuthenticationFailedException extends HttpException {
    public AuthenticationFailedException() {
        super(HttpStatus.UNAUTHORIZED, "Authentication failed");
    }
}