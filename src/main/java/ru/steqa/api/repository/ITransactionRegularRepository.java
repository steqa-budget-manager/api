package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.TransactionRegular;

import java.util.List;

public interface ITransactionRegularRepository extends JpaRepository<TransactionRegular, Long> {
    List<TransactionRegular> findAllByUserId(Long userId);
}
