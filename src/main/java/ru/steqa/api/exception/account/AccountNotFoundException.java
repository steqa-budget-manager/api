package ru.steqa.api.exception.account;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;


public class AccountNotFoundException extends HttpException {
    public AccountNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Account not found");
    }
}
