package ru.steqa.api.exception.transaction.category;

public class TransactionCategoryNotFoundException extends RuntimeException {
    public TransactionCategoryNotFoundException() {
        super("Transaction category not found");
    }
}
