package ru.steqa.api.exception.transfer;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class TransferNotFoundException extends HttpException {
    public TransferNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Transfer not found");
    }
}
