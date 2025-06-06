package ru.steqa.api.service.transaction.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
import ru.steqa.api.scheme.transaction.category.TransactionCategoryFilter;
import ru.steqa.api.scheme.transaction.category.UpdateTransactionCategoryScheme;
import ru.steqa.api.specification.TransactionCategorySpecification;

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
                .type(request.getType())
                .name(request.getName())
                .user(user)
                .build();

        TransactionCategory transactionCategory = transactionCategoryRepository.save(transactionCategoryToAdd);

        return toResponseScheme(transactionCategory);
    }

    @Override
    public List<ResponseTransactionCategoryScheme> getTransactionCategories(Long userId) {
        return transactionCategoryRepository.findAllByUserId(userId, Sort.by(Sort.Direction.ASC, "createdAt"))
                .stream()
                .map(this::toResponseScheme)
                .toList();
    }

    @Override
    public List<ResponseTransactionCategoryScheme> getTransactionCategories(Long userId, TransactionCategoryFilter filter) {
        Specification<TransactionCategory> spec = TransactionCategorySpecification.byFilter(userId, filter);
        return transactionCategoryRepository.findAll(spec, Sort.by(Sort.Direction.ASC, "createdAt"))
                .stream()
                .map(this::toResponseScheme)
                .toList();
    }

    @Override
    public ResponseTransactionCategoryScheme getTransactionCategoryById(Long userId, Long id) {
        TransactionCategory transactionCategory = transactionCategoryRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionCategoryNotFoundException::new);

        return toResponseScheme(transactionCategory);
    }

    @Override
    public ResponseTransactionCategoryScheme updateTransactionCategory(Long userId, Long id, UpdateTransactionCategoryScheme request) {
        TransactionCategory transactionCategoryToUpdate = transactionCategoryRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionCategoryNotFoundException::new);

        if (request.getType() != null) transactionCategoryToUpdate.setType(request.getType());
        if (request.getName() != null) transactionCategoryToUpdate.setName(request.getName());
        if (request.getVisible() != null) transactionCategoryToUpdate.setVisible(request.getVisible());

        TransactionCategory transactionCategory = transactionCategoryRepository.save(transactionCategoryToUpdate);

        return toResponseScheme(transactionCategory);
    }

    @Override
    public void deleteTransactionCategoryById(Long userId, Long id) {
        TransactionCategory transactionCategory = transactionCategoryRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionCategoryNotFoundException::new);

        List<Transaction> transactions = transactionRepository.findAllByUserIdAndCategoryId(userId, transactionCategory.getId());
        if (!transactions.isEmpty()) throw new TransactionCategoryHasTransactionsException();

        transactionCategoryRepository.delete(transactionCategory);
    }

    private ResponseTransactionCategoryScheme toResponseScheme(TransactionCategory transactionCategory) {
        return ResponseTransactionCategoryScheme.builder()
                .id(transactionCategory.getId())
                .type(transactionCategory.getType())
                .name(transactionCategory.getName())
                .visible(transactionCategory.getVisible())
                .createdAt(transactionCategory.getCreatedAt())
                .build();
    }
}
