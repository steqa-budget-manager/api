package ru.steqa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.steqa.api.model.Account;

public interface IAccountRepository extends JpaRepository<Account, Long> {
}
