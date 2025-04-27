package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.TransactionRegular;
import ru.steqa.api.model.User;

import java.util.List;
import java.util.Optional;

public interface ITransactionRegularRepository extends JpaRepository<TransactionRegular, Long> {
    List<TransactionRegular> findAllByUserId(Long userId);
    Optional<TransactionRegular> findByUserIdAndId(Long userId, Long id);

    Long user(User user);
}
