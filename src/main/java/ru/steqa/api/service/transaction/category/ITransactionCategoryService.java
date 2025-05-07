package ru.steqa.api.service.transaction.category;

import ru.steqa.api.scheme.transaction.category.AddTransactionCategoryScheme;
import ru.steqa.api.scheme.transaction.category.ResponseTransactionCategoryScheme;
import ru.steqa.api.scheme.transaction.category.TransactionCategoryFilter;
import ru.steqa.api.scheme.transaction.category.UpdateTransactionCategoryScheme;

import java.util.List;

public interface ITransactionCategoryService {
    ResponseTransactionCategoryScheme addTransactionCategory(Long userId, AddTransactionCategoryScheme transactionCategory);
    List<ResponseTransactionCategoryScheme> getTransactionCategories(Long userId);
    List<ResponseTransactionCategoryScheme> getTransactionCategories(Long userId, TransactionCategoryFilter filter);
    ResponseTransactionCategoryScheme getTransactionCategoryById(Long userId, Long id);
    ResponseTransactionCategoryScheme updateTransactionCategory(Long userId, Long id, UpdateTransactionCategoryScheme transactionCategory);
    void deleteTransactionCategoryById(Long userId, Long id);
}
