package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.TransactionCategory;

import java.util.List;
import java.util.Optional;

public interface ITransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
    List<TransactionCategory> findAllByUserId(Long userId);
    Optional<TransactionCategory> findByUserIdAndId(Long userId, Long id);
}
