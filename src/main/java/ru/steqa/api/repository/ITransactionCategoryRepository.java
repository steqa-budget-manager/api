package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.TransactionCategory;

import java.util.Optional;

public interface ITransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
    Optional<TransactionCategory> findByUserIdAndId(Long userId, Long id);
}
