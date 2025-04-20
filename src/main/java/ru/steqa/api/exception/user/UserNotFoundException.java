package ru.steqa.api.exception.user;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class UserNotFoundException extends HttpException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User not found");
    }
}
