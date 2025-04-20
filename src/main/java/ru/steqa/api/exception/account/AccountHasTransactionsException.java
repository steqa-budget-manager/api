package ru.steqa.api.exception.account;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class AccountHasTransactionsException extends HttpException {
    public AccountHasTransactionsException() {
        super(HttpStatus.CONFLICT, "Account has transactions and cannot be deleted");
    }
}
