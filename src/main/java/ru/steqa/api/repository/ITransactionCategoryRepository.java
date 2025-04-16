package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.TransactionCategory;

public interface ITransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
}
