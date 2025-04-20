package ru.steqa.api.service.transaction;

import ru.steqa.api.schema.transaction.AddTransactionSchema;
import ru.steqa.api.schema.transaction.ResponseTransactionSchema;
import ru.steqa.api.schema.transaction.UpdateTransactionSchema;

import java.util.List;

public interface ITransactionService {
    ResponseTransactionSchema addTransaction(Long userId, AddTransactionSchema transaction);
    List<ResponseTransactionSchema> getTransactions(Long userId);
    ResponseTransactionSchema getTransactionById(Long userId, Long id);
    ResponseTransactionSchema updateTransaction(Long userId, Long id, UpdateTransactionSchema transaction);
    void deleteTransactionById(Long userId, Long id);
}
