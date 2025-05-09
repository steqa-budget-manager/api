package ru.steqa.api.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.Transfer;

import java.util.List;
import java.util.Optional;

public interface ITransferRepository extends JpaRepository<Transfer, Long> {
    @EntityGraph(attributePaths = {"fromAccount", "toAccount"})
    List<Transfer> findAllByUserId(Long userId, Sort sort);
    @EntityGraph(attributePaths = {"fromAccount", "toAccount"})
    Optional<Transfer> findByUserIdAndId(Long userId, Long id);
    List<Transfer> findAllByUserIdAndFromAccountIdOrUserIdAndToAccountId(Long userId, Long fromAccountId, Long userId2, Long toAccountId);
}
