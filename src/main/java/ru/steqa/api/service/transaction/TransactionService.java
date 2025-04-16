package ru.steqa.api.service.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.steqa.api.exception.account.AccountNotFoundException;
import ru.steqa.api.exception.transaction.TransactionNotFoundException;
import ru.steqa.api.exception.transaction.category.TransactionCategoryNotFoundException;
import ru.steqa.api.model.Account;
import ru.steqa.api.model.Transaction;
import ru.steqa.api.model.TransactionCategory;
import ru.steqa.api.repository.IAccountRepository;
import ru.steqa.api.repository.ITransactionCategoryRepository;
import ru.steqa.api.repository.ITransactionRepository;
import ru.steqa.api.schema.transaction.AddTransactionSchema;
import ru.steqa.api.schema.transaction.ResponseTransactionSchema;
import ru.steqa.api.schema.transaction.UpdateTransactionSchema;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {
    private final ITransactionRepository transactionRepository;
    private final IAccountRepository accountRepository;
    private final ITransactionCategoryRepository transactionCategoryRepository;

    @Override
    public ResponseTransactionSchema addTransaction(AddTransactionSchema request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(AccountNotFoundException::new);

        TransactionCategory category = transactionCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(TransactionCategoryNotFoundException::new);

        Transaction transactionToAdd = Transaction.builder()
                .type(request.getType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .account(account)
                .category(category)
                .build();
        Transaction transaction = transactionRepository.save(transactionToAdd);
        return ResponseTransactionSchema.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .accountId(transaction.getAccountId())
                .categoryId(transaction.getCategoryId())
                .build();
    }

    @Override
    public List<ResponseTransactionSchema> getTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> ResponseTransactionSchema.builder()
                        .id(transaction.getId())
                        .type(transaction.getType())
                        .amount(transaction.getAmount())
                        .description(transaction.getDescription())
                        .date(transaction.getDate())
                        .accountId(transaction.getAccountId())
                        .categoryId(transaction.getCategoryId())
                        .build()
                )
                .toList();
    }

    @Override
    public ResponseTransactionSchema getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(TransactionNotFoundException::new);
        return ResponseTransactionSchema.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .accountId(transaction.getAccountId())
                .categoryId(transaction.getCategoryId())
                .build();
    }

    @Override
    public ResponseTransactionSchema updateTransaction(UpdateTransactionSchema request, Long id) {
        Transaction transactionToUpdate = transactionRepository.findById(id)
                .orElseThrow(TransactionNotFoundException::new);

        if (request.getAccountId() != null) {
            Account account = accountRepository.findById(request.getAccountId())
                    .orElseThrow(AccountNotFoundException::new);
            transactionToUpdate.setAccount(account);
            transactionToUpdate.setAccountId(account.getId());
        }

        if (request.getCategoryId() != null) {
            TransactionCategory category = transactionCategoryRepository.findById(request.getCategoryId())
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
        return ResponseTransactionSchema.builder()
                .id(transaction.getId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .date(transaction.getDate())
                .accountId(transaction.getAccountId())
                .categoryId(transaction.getCategoryId())
                .build();
    }

    @Override
    public void deleteTransactionById(Long id) {
        transactionRepository.findById(id)
                .ifPresentOrElse(transactionRepository::delete, () -> {
                    throw new TransactionNotFoundException();
                });
    }
}
