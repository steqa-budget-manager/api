package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
