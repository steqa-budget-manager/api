package ru.steqa.api.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import ru.steqa.api.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    @NonNull
    @EntityGraph(attributePaths = {"account", "category"})
    List<Transaction> findAll(Specification<Transaction> spec, @NonNull Sort sort);
    @EntityGraph(attributePaths = {"account", "category"})
    List<Transaction> findAllByUserId(Long userId, Sort sort);
    List<Transaction> findAllByUserIdAndAccountId(Long userId, Long id);
    List<Transaction> findAllByUserIdAndCategoryId(Long userId, Long id);
    Optional<Transaction> findByUserIdAndId(Long userId, Long id);
}
