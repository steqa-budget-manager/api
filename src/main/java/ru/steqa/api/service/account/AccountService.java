package ru.steqa.api.service.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.steqa.api.exception.account.AccountHasTransactionsException;
import ru.steqa.api.exception.account.AccountHasTransfersException;
import ru.steqa.api.exception.account.AccountNotFoundException;
import ru.steqa.api.exception.user.UserNotFoundException;
import ru.steqa.api.model.Account;
import ru.steqa.api.model.Transaction;
import ru.steqa.api.model.Transfer;
import ru.steqa.api.model.User;
import ru.steqa.api.repository.IAccountRepository;
import ru.steqa.api.repository.ITransactionRepository;
import ru.steqa.api.repository.ITransferRepository;
import ru.steqa.api.repository.IUserRepository;
import ru.steqa.api.scheme.account.AddAccountScheme;
import ru.steqa.api.scheme.account.ResponseAccountScheme;
import ru.steqa.api.scheme.account.UpdateAccountScheme;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService {
    private final IAccountRepository accountRepository;
    private final IUserRepository userRepository;
    private final ITransactionRepository transactionRepository;
    private final ITransferRepository transferRepository;

    @Override
    public ResponseAccountScheme addAccount(Long userId, AddAccountScheme request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Account accountToAdd = Account.builder()
                .name(request.getName())
                .user(user)
                .build();

        Account account = accountRepository.save(accountToAdd);

        return toResponseScheme(account);
    }

    @Override
    public List<ResponseAccountScheme> getAccounts(Long userId) {
        return accountRepository.findAllByUserId(userId)
                .stream()
                .map(this::toResponseScheme)
                .toList();
    }

    @Override
    public ResponseAccountScheme getAccountById(Long userId, Long id) {
        Account account = accountRepository.findByUserIdAndId(userId, id)
                .orElseThrow(AccountNotFoundException::new);

        return toResponseScheme(account);
    }

    @Override
    public ResponseAccountScheme updateAccount(Long userId, Long id, UpdateAccountScheme request) {
        Account accountToUpdate = accountRepository.findByUserIdAndId(userId, id)
                .orElseThrow(AccountNotFoundException::new);

        if (request.getName() != null) accountToUpdate.setName(request.getName());
        if (request.getVisible() != null) accountToUpdate.setVisible(request.getVisible());

        Account account = accountRepository.save(accountToUpdate);

        return toResponseScheme(account);
    }

    @Override
    public void deleteAccountById(Long userId, Long id) {
        Account account = accountRepository.findByUserIdAndId(userId, id)
                .orElseThrow(AccountNotFoundException::new);

        List<Transaction> transactions = transactionRepository.findAllByUserIdAndAccountId(userId, account.getId());
        if (!transactions.isEmpty()) throw new AccountHasTransactionsException();

        List<Transfer> transfers = transferRepository.findAllByUserIdAndFromAccountIdOrUserIdAndToAccountId(userId, account.getId(), userId, account.getId());
        if (!transfers.isEmpty()) throw new AccountHasTransfersException();

        accountRepository.delete(account);
    }

    private ResponseAccountScheme toResponseScheme(Account account) {
        return ResponseAccountScheme.builder()
                .id(account.getId())
                .name(account.getName())
                .visible(account.getVisible())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
