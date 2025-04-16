package ru.steqa.api.exception.transaction;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class TransactionNotFoundException extends HttpException {
    public TransactionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Transaction not found");
    }
}
