package ru.steqa.api.service.transaction.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.steqa.api.exception.transaction.category.TransactionCategoryHasTransactionsException;
import ru.steqa.api.exception.transaction.category.TransactionCategoryNotFoundException;
import ru.steqa.api.exception.user.UserNotFoundException;
import ru.steqa.api.model.Transaction;
import ru.steqa.api.model.TransactionCategory;
import ru.steqa.api.model.User;
import ru.steqa.api.repository.ITransactionCategoryRepository;
import ru.steqa.api.repository.ITransactionRepository;
import ru.steqa.api.repository.IUserRepository;
import ru.steqa.api.scheme.transaction.category.AddTransactionCategoryScheme;
import ru.steqa.api.scheme.transaction.category.ResponseTransactionCategoryScheme;
import ru.steqa.api.scheme.transaction.category.UpdateTransactionCategoryScheme;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionCategoryService implements ITransactionCategoryService {
    private final ITransactionCategoryRepository transactionCategoryRepository;
    private final IUserRepository userRepository;
    private final ITransactionRepository transactionRepository;

    @Override
    public ResponseTransactionCategoryScheme addTransactionCategory(Long userId, AddTransactionCategoryScheme request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        TransactionCategory transactionCategoryToAdd = TransactionCategory.builder()
                .name(request.getName())
                .user(user)
                .build();
        TransactionCategory transactionCategory = transactionCategoryRepository.save(transactionCategoryToAdd);
        return ResponseTransactionCategoryScheme.builder()
                .id(transactionCategory.getId())
                .name(transactionCategory.getName())
                .visible(transactionCategory.getVisible())
                .build();
    }

    @Override
    public List<ResponseTransactionCategoryScheme> getTransactionCategories(Long userId) {
        return transactionCategoryRepository.findAllByUserId(userId)
                .stream()
                .map(transactionCategory -> ResponseTransactionCategoryScheme.builder()
                        .id(transactionCategory.getId())
                        .name(transactionCategory.getName())
                        .visible(transactionCategory.getVisible())
                        .build()
                )
                .toList();
    }

    @Override
    public ResponseTransactionCategoryScheme getTransactionCategoryById(Long userId, Long id) {
        TransactionCategory transactionCategory = transactionCategoryRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionCategoryNotFoundException::new);
        return ResponseTransactionCategoryScheme.builder()
                .id(transactionCategory.getId())
                .name(transactionCategory.getName())
                .visible(transactionCategory.getVisible())
                .build();
    }

    @Override
    public ResponseTransactionCategoryScheme updateTransactionCategory(Long userId, Long id, UpdateTransactionCategoryScheme request) {
        TransactionCategory transactionCategoryToUpdate = transactionCategoryRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionCategoryNotFoundException::new);

        if (request.getName() != null) transactionCategoryToUpdate.setName(request.getName());
        if (request.getVisible() != null) transactionCategoryToUpdate.setVisible(request.getVisible());

        TransactionCategory transactionCategory = transactionCategoryRepository.save(transactionCategoryToUpdate);
        return ResponseTransactionCategoryScheme.builder()
                .id(transactionCategory.getId())
                .name(transactionCategory.getName())
                .visible(transactionCategory.getVisible())
                .build();
    }

    @Override
    public void deleteTransactionCategoryById(Long userId, Long id) {
        TransactionCategory transactionCategory = transactionCategoryRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionCategoryNotFoundException::new);

        List<Transaction> transactions = transactionRepository.findAllByUserIdAndCategoryId(userId, transactionCategory.getId());
        if (!transactions.isEmpty()) throw new TransactionCategoryHasTransactionsException();

        transactionCategoryRepository.delete(transactionCategory);
    }
}
