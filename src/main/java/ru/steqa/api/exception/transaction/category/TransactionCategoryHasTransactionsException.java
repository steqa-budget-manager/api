package ru.steqa.api.exception.transaction.category;

import org.springframework.http.HttpStatus;
import ru.steqa.api.exception.HttpException;

public class TransactionCategoryHasTransactionsException extends HttpException {
  public TransactionCategoryHasTransactionsException() {
    super(HttpStatus.CONFLICT, "Category has transactions and cannot be deleted");
  }
}
