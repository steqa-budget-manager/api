package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUserId(Long userId);
    List<Transaction> findAllByUserIdAndAccountId(Long userId, Long id);
    Optional<Transaction> findByUserIdAndId(Long userId, Long id);
}
