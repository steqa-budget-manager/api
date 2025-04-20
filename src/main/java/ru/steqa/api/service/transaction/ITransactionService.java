package ru.steqa.api.service.transaction;

import ru.steqa.api.scheme.transaction.AddTransactionScheme;
import ru.steqa.api.scheme.transaction.ResponseTransactionScheme;
import ru.steqa.api.scheme.transaction.UpdateTransactionScheme;

import java.util.List;

public interface ITransactionService {
    ResponseTransactionScheme addTransaction(Long userId, AddTransactionScheme transaction);
    List<ResponseTransactionScheme> getTransactions(Long userId);
    ResponseTransactionScheme getTransactionById(Long userId, Long id);
    ResponseTransactionScheme updateTransaction(Long userId, Long id, UpdateTransactionScheme transaction);
    void deleteTransactionById(Long userId, Long id);
}
