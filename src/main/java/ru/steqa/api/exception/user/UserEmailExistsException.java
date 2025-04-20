package ru.steqa.api.exception.user;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class UserEmailExistsException extends HttpException {
    public UserEmailExistsException() {
        super(HttpStatus.CONFLICT, "User with this email already exists");
    }
}
