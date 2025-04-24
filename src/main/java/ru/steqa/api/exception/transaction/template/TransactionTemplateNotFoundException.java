package ru.steqa.api.exception.transaction.template;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class TransactionTemplateNotFoundException extends HttpException {
    public TransactionTemplateNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Transaction template not found");
    }
}
