package ru.steqa.api.exception.account;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class AccountHasTransfersException extends HttpException {
    public AccountHasTransfersException() {
        super(HttpStatus.CONFLICT, "Account has transfers and cannot be deleted");
    }
}
