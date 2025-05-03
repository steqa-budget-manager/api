package ru.steqa.api.service.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.steqa.api.exception.account.AccountNotFoundException;
import ru.steqa.api.exception.transaction.TransactionNotFoundException;
import ru.steqa.api.exception.transaction.category.TransactionCategoryNotFoundException;
import ru.steqa.api.exception.user.UserNotFoundException;
import ru.steqa.api.model.*;
import ru.steqa.api.repository.IAccountRepository;
import ru.steqa.api.repository.ITransactionCategoryRepository;
import ru.steqa.api.repository.ITransactionRepository;
import ru.steqa.api.repository.IUserRepository;
import ru.steqa.api.scheme.transaction.AddTransactionScheme;
import ru.steqa.api.scheme.transaction.ResponseTransactionScheme;
import ru.steqa.api.scheme.transaction.TransactionFilter;
import ru.steqa.api.scheme.transaction.UpdateTransactionScheme;
import ru.steqa.api.specification.TransactionSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {
    private final ITransactionRepository transactionRepository;
    private final IUserRepository userRepository;
    private final IAccountRepository accountRepository;
    private final ITransactionCategoryRepository transactionCategoryRepository;

    @Override
    public ResponseTransactionScheme addTransaction(Long userId, AddTransactionScheme request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Account account = accountRepository.findByUserIdAndId(userId, request.getAccountId())
                .orElseThrow(AccountNotFoundException::new);

        TransactionCategory category = transactionCategoryRepository.findByUserIdAndId(userId, request.getCategoryId())
                .orElseThrow(TransactionCategoryNotFoundException::new);

        Transaction transactionToAdd = Transaction.builder()
                .type(request.getType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .user(user)
                .account(account)
                .category(category)
                .build();

        Transaction transaction = transactionRepository.save(transactionToAdd);

        return toResponseScheme(transaction);
    }

    @Override
    public List<ResponseTransactionScheme> getTransactions(Long userId) {
        return transactionRepository.findAllByUserId(userId, Sort.by(Sort.Direction.DESC, "date"))
                .stream()
                .map(this::toResponseScheme)
                .toList();
    }

    @Override
    public List<ResponseTransactionScheme> getTransactions(Long userId, TransactionFilter filter) {
        Specification<Transaction> spec = TransactionSpecification.byFilter(userId, filter);
        return transactionRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "date"))
                .stream()
                .map(this::toResponseScheme)
                .toList();
    }

    @Override
    public ResponseTransactionScheme getTransactionById(Long userId, Long id) {
        Transaction transaction = transactionRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionNotFoundException::new);

        return toResponseScheme(transaction);
    }

    @Override
    public ResponseTransactionScheme updateTransaction(Long userId, Long id, UpdateTransactionScheme request) {
        Transaction transactionToUpdate = transactionRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionNotFoundException::new);

        if (request.getAccountId() != null) {
            Account account = accountRepository.findByUserIdAndId(userId, request.getAccountId())
                    .orElseThrow(AccountNotFoundException::new);
            transactionToUpdate.setAccount(account);
            transactionToUpdate.setAccountId(account.getId());
        }

        if (request.getCategoryId() != null) {
            TransactionCategory category = transactionCategoryRepository.findByUserIdAndId(userId, request.getCategoryId())
                    .orElseThrow(TransactionCategoryNotFoundException::new);
            transactionToUpdate.setCategory(category);
            transactionToUpdate.setCategoryId(category.getId());
        }

        if (request.getType() != null) transactionToUpdate.setType(request.getType());
        if (request.getAmount() != null) transactionToUpdate.setAmount(request.getAmount());
        if (request.getDescription() != null) transactionToUpdate.setDescription(request.getDescription());
        if (request.getDate() != null) transactionToUpdate.setDate(request.getDate());
        if (request.getType() != null) transactionToUpdate.setType(request.getType());

        Transaction transaction = transactionRepository.save(transactionToUpdate);

        return toResponseScheme(transaction);
    }

    @Override
    public void deleteTransactionById(Long userId, Long id) {
        transactionRepository.findByUserIdAndId(userId, id)
                .ifPresentOrElse(transactionRepository::delete, () -> {
                    throw new TransactionNotFoundException();
                });
    }

    private ResponseTransactionScheme toResponseScheme(Transaction transaction) {
        return ResponseTransactionScheme.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .account(transaction.getAccount().getName())
                .accountId(transaction.getAccountId())
                .category(transaction.getCategory().getName())
                .categoryId(transaction.getCategoryId())
                .build();
    }
}
