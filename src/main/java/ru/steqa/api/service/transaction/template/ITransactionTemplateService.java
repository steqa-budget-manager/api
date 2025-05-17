package ru.steqa.api.service.transaction.template;

import ru.steqa.api.scheme.transaction.template.AddTransactionTemplateScheme;
import ru.steqa.api.scheme.transaction.template.ResponseTransactionTemplateScheme;
import ru.steqa.api.scheme.transaction.template.TransactionTemplateFilter;
import ru.steqa.api.scheme.transaction.template.UpdateTransactionTemplateScheme;

import java.util.List;

public interface ITransactionTemplateService {
    ResponseTransactionTemplateScheme addTransactionTemplate(Long userId, AddTransactionTemplateScheme transactionTemplate);
    List<ResponseTransactionTemplateScheme> getTransactionTemplates(Long userId, TransactionTemplateFilter filter);
    ResponseTransactionTemplateScheme getTransactionTemplateById(Long userId, Long id);
    ResponseTransactionTemplateScheme updateTransactionTemplate(Long userId, Long id, UpdateTransactionTemplateScheme transactionTemplate);
    void deleteTransactionTemplateById(Long userId, Long id);
}
