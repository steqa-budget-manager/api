package ru.steqa.api.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.TransactionTemplate;

import java.util.List;
import java.util.Optional;

public interface ITransactionTemplateRepository extends JpaRepository<TransactionTemplate, Long> {
    @EntityGraph(attributePaths = {"account", "category"})
    List<TransactionTemplate> findAll(Specification<TransactionTemplate> spec, Sort sort);
    @EntityGraph(attributePaths = {"account", "category"})
    Optional<TransactionTemplate> findByUserIdAndId(Long userId, Long id);
}
