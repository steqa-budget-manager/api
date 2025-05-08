package ru.steqa.api.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAll(Specification<Account> spec, Sort sort);
    List<Account> findAllByUserId(Long userId, Sort sort);
    Optional<Account> findByUserIdAndId(Long userId, Long id);
}
