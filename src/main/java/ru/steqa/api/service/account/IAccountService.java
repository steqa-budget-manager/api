package ru.steqa.api.service.account;

import ru.steqa.api.scheme.account.AddAccountScheme;
import ru.steqa.api.scheme.account.ResponseAccountScheme;
import ru.steqa.api.scheme.account.UpdateAccountScheme;

import java.util.List;

public interface IAccountService {
    ResponseAccountScheme addAccount(Long userId, AddAccountScheme account);
    List<ResponseAccountScheme> getAccounts(Long userId);
    ResponseAccountScheme getAccountById(Long userId, Long id);
    ResponseAccountScheme updateAccount(Long userId, Long id, UpdateAccountScheme account);
    void deleteAccountById(Long userId, Long id);
}
