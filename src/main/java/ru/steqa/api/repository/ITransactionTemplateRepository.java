package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.TransactionTemplate;

import java.util.List;
import java.util.Optional;

public interface ITransactionTemplateRepository extends JpaRepository<TransactionTemplate, Long> {
    List<TransactionTemplate> findAllByUserId(Long userId);
    Optional<TransactionTemplate> findByUserIdAndId(Long userId, Long id);
}
