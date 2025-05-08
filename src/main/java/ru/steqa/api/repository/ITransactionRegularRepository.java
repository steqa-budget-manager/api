package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.TransactionRegular;

import java.util.List;
import java.util.Optional;

public interface ITransactionRegularRepository extends JpaRepository<TransactionRegular, Long> {
    @EntityGraph(attributePaths = {"account", "category"})
    List<TransactionRegular> findAllByUserId(Long userId);
    Optional<TransactionRegular> findByUserIdAndId(Long userId, Long id);
}
