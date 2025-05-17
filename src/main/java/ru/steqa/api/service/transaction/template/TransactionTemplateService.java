package ru.steqa.api.service.transaction.template;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.steqa.api.exception.account.AccountNotFoundException;
import ru.steqa.api.exception.transaction.category.TransactionCategoryNotFoundException;
import ru.steqa.api.exception.transaction.template.TransactionTemplateNotFoundException;
import ru.steqa.api.exception.user.UserNotFoundException;
import ru.steqa.api.model.Account;
import ru.steqa.api.model.TransactionCategory;
import ru.steqa.api.model.TransactionTemplate;
import ru.steqa.api.model.User;
import ru.steqa.api.repository.IAccountRepository;
import ru.steqa.api.repository.ITransactionCategoryRepository;
import ru.steqa.api.repository.ITransactionTemplateRepository;
import ru.steqa.api.repository.IUserRepository;
import ru.steqa.api.scheme.transaction.template.AddTransactionTemplateScheme;
import ru.steqa.api.scheme.transaction.template.ResponseTransactionTemplateScheme;
import ru.steqa.api.scheme.transaction.template.TransactionTemplateFilter;
import ru.steqa.api.scheme.transaction.template.UpdateTransactionTemplateScheme;
import ru.steqa.api.specification.TransactionTemplateSpecification;

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

        return toResponseScheme(transactionTemplate);
    }

    @Override
    public List<ResponseTransactionTemplateScheme> getTransactionTemplates(Long userId, TransactionTemplateFilter filter) {
        Specification<TransactionTemplate> spec = TransactionTemplateSpecification.byFilter(userId, filter);
        return transactionTemplateRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "createdAt"))
                .stream()
                .map(this::toResponseScheme)
                .toList();
    }

    @Override
    public ResponseTransactionTemplateScheme getTransactionTemplateById(Long userId, Long id) {
        TransactionTemplate transactionTemplate = transactionTemplateRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionTemplateNotFoundException::new);

        return toResponseScheme(transactionTemplate);
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

        return toResponseScheme(transactionTemplate);
    }

    @Override
    public void deleteTransactionTemplateById(Long userId, Long id) {
        transactionTemplateRepository.findByUserIdAndId(userId, id)
                .ifPresentOrElse(transactionTemplateRepository::delete, () -> {
                    throw new TransactionTemplateNotFoundException();
                });
    }

    private ResponseTransactionTemplateScheme toResponseScheme(TransactionTemplate transactionTemplate) {
        return ResponseTransactionTemplateScheme.builder()
                .id(transactionTemplate.getId())
                .type(transactionTemplate.getType())
                .amount(transactionTemplate.getAmount())
                .description(transactionTemplate.getDescription())
                .createdAt(transactionTemplate.getCreatedAt())
                .account(transactionTemplate.getAccount().getName())
                .accountId(transactionTemplate.getAccountId())
                .category(transactionTemplate.getCategory().getName())
                .categoryId(transactionTemplate.getCategoryId())
                .build();
    }
}
