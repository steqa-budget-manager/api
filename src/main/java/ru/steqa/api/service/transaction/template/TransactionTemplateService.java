package ru.steqa.api.service.transaction.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.steqa.api.exception.account.AccountNotFoundException;
import ru.steqa.api.exception.transaction.category.TransactionCategoryNotFoundException;
import ru.steqa.api.exception.transaction.template.TransactionTemplateNotFoundException;
import ru.steqa.api.exception.user.UserNotFoundException;
import ru.steqa.api.model.*;
import ru.steqa.api.repository.*;
import ru.steqa.api.scheme.transaction.template.AddTransactionTemplateScheme;
import ru.steqa.api.scheme.transaction.template.ResponseTransactionTemplateScheme;
import ru.steqa.api.scheme.transaction.template.UpdateTransactionTemplateScheme;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionTemplateService implements ITransactionTemplateService {
    private final ITransactionTemplateRepository transactionTemplateRepository;
    private final IUserRepository userRepository;
    private final IAccountRepository accountRepository;
    private final ITransactionCategoryRepository transactionCategoryRepository;

    @Override
    public ResponseTransactionTemplateScheme addTransactionTemplate(Long userId, AddTransactionTemplateScheme request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Account account = accountRepository.findByUserIdAndId(userId, request.getAccountId())
                .orElseThrow(AccountNotFoundException::new);

        TransactionCategory category = transactionCategoryRepository.findByUserIdAndId(userId, request.getCategoryId())
                .orElseThrow(TransactionCategoryNotFoundException::new);

        TransactionTemplate transactionTemplateToAdd = TransactionTemplate.builder()
                .type(request.getType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .user(user)
                .account(account)
                .category(category)
                .build();
        TransactionTemplate transactionTemplate = transactionTemplateRepository.save(transactionTemplateToAdd);
        return ResponseTransactionTemplateScheme.builder()
                .id(transactionTemplate.getId())
                .type(transactionTemplate.getType())
                .amount(transactionTemplate.getAmount())
                .description(transactionTemplate.getDescription())
                .accountId(transactionTemplate.getAccountId())
                .categoryId(transactionTemplate.getCategoryId())
                .build();
    }

    @Override
    public List<ResponseTransactionTemplateScheme> getTransactionTemplates(Long userId) {
        return transactionTemplateRepository.findAllByUserId(userId)
                .stream()
                .map(transactionTemplate -> ResponseTransactionTemplateScheme.builder()
                        .id(transactionTemplate.getId())
                        .type(transactionTemplate.getType())
                        .amount(transactionTemplate.getAmount())
                        .description(transactionTemplate.getDescription())
                        .accountId(transactionTemplate.getAccountId())
                        .categoryId(transactionTemplate.getCategoryId())
                        .build()
                )
                .toList();
    }

    @Override
    public ResponseTransactionTemplateScheme getTransactionTemplateById(Long userId, Long id) {
        TransactionTemplate transactionTemplate = transactionTemplateRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionTemplateNotFoundException::new);
        return ResponseTransactionTemplateScheme.builder()
                .id(transactionTemplate.getId())
                .type(transactionTemplate.getType())
                .amount(transactionTemplate.getAmount())
                .description(transactionTemplate.getDescription())
                .accountId(transactionTemplate.getAccountId())
                .categoryId(transactionTemplate.getCategoryId())
                .build();
    }

    @Override
    public ResponseTransactionTemplateScheme updateTransactionTemplate(Long userId, Long id, UpdateTransactionTemplateScheme request) {
        TransactionTemplate transactionTemplateToUpdate = transactionTemplateRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionTemplateNotFoundException::new);

        if (request.getAccountId() != null) {
            Account account = accountRepository.findByUserIdAndId(userId, request.getAccountId())
                    .orElseThrow(AccountNotFoundException::new);
            transactionTemplateToUpdate.setAccount(account);
            transactionTemplateToUpdate.setAccountId(account.getId());
        }

        if (request.getCategoryId() != null) {
            TransactionCategory category = transactionCategoryRepository.findByUserIdAndId(userId, request.getCategoryId())
                    .orElseThrow(TransactionCategoryNotFoundException::new);
            transactionTemplateToUpdate.setCategory(category);
            transactionTemplateToUpdate.setCategoryId(category.getId());
        }

        if (request.getType() != null) transactionTemplateToUpdate.setType(request.getType());
        if (request.getAmount() != null) transactionTemplateToUpdate.setAmount(request.getAmount());
        if (request.getDescription() != null) transactionTemplateToUpdate.setDescription(request.getDescription());
        if (request.getType() != null) transactionTemplateToUpdate.setType(request.getType());

        TransactionTemplate transactionTemplate = transactionTemplateRepository.save(transactionTemplateToUpdate);
        return ResponseTransactionTemplateScheme.builder()
                .id(transactionTemplate.getId())
                .type(transactionTemplate.getType())
                .amount(transactionTemplate.getAmount())
                .description(transactionTemplate.getDescription())
                .accountId(transactionTemplate.getAccountId())
                .categoryId(transactionTemplate.getCategoryId())
                .build();
    }

    @Override
    public void deleteTransactionTemplateById(Long userId, Long id) {
        transactionTemplateRepository.findByUserIdAndId(userId, id)
                .ifPresentOrElse(transactionTemplateRepository::delete, () -> {
                    throw new TransactionTemplateNotFoundException();
                });
    }
}
