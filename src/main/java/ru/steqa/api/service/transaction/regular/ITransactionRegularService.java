package ru.steqa.api.service.transaction.regular;

import ru.steqa.api.scheme.transaction.regular.AddTransactionRegularScheme;
import ru.steqa.api.scheme.transaction.regular.ResponseTransactionRegularScheme;
import ru.steqa.api.scheme.transaction.regular.TransactionRegularFilter;

import java.util.List;

public interface ITransactionRegularService {
    ResponseTransactionRegularScheme addTransactionRegular(Long userId, AddTransactionRegularScheme transactionRegular);
    List<ResponseTransactionRegularScheme> getTransactionRegulars(Long userId, TransactionRegularFilter filter);
    void deleteTransactionRegularById(Long userId, Long id);
}
