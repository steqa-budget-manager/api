package ru.steqa.api.exception.transaction.category;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class TransactionCategoryNotFoundException extends HttpException {
    public TransactionCategoryNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Category not found");
    }
}
