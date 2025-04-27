package ru.steqa.api.exception.transaction.regular;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class DeleteTransactionRegularException extends HttpException {
    public DeleteTransactionRegularException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Error when deleting a regular transaction");
    }
}
