package ru.steqa.api.service.transaction;

import ru.steqa.api.schema.transaction.AddTransactionSchema;
import ru.steqa.api.schema.transaction.ResponseTransactionSchema;
import ru.steqa.api.schema.transaction.UpdateTransactionSchema;

import java.util.List;

public interface ITransactionService {
    ResponseTransactionSchema addTransaction(AddTransactionSchema transaction);
    List<ResponseTransactionSchema> getTransactions();
    ResponseTransactionSchema getTransactionById(Long id);
    ResponseTransactionSchema updateTransaction(UpdateTransactionSchema transaction, Long id);
    void deleteTransactionById(Long id);
}
