package ru.steqa.api.service.transfer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.steqa.api.exception.account.AccountNotFoundException;
import ru.steqa.api.exception.transfer.TransferNotFoundException;
import ru.steqa.api.exception.user.UserNotFoundException;
import ru.steqa.api.model.Account;
import ru.steqa.api.model.Transfer;
import ru.steqa.api.model.User;
import ru.steqa.api.repository.IAccountRepository;
import ru.steqa.api.repository.ITransferRepository;
import ru.steqa.api.repository.IUserRepository;
import ru.steqa.api.scheme.transfer.AddTransferScheme;
import ru.steqa.api.scheme.transfer.ResponseTransferScheme;
import ru.steqa.api.scheme.transfer.UpdateTransferScheme;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService implements ITransferService {
    private final ITransferRepository transferRepository;
    private final IUserRepository userRepository;
    private final IAccountRepository accountRepository;

    @Override
    public ResponseTransferScheme addTransfer(Long userId, AddTransferScheme request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Account fromAccount = accountRepository.findByUserIdAndId(userId, request.getFromAccountId())
                .orElseThrow(AccountNotFoundException::new);

        Account toAccount = accountRepository.findByUserIdAndId(userId, request.getToAccountId())
                .orElseThrow(AccountNotFoundException::new);

        Transfer transferToAdd = Transfer.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .user(user)
                .fromAccount(fromAccount)
                .toAccount(toAccount)
                .build();

        Transfer transfer = transferRepository.save(transferToAdd);

        return toResponseScheme(transfer);
    }

    @Override
    public List<ResponseTransferScheme> getTransfers(Long userId) {
        return transferRepository.findAllByUserId(userId)
                .stream()
                .map(this::toResponseScheme)
                .toList();
    }

    @Override
    public ResponseTransferScheme getTransferById(Long userId, Long id) {
        Transfer transfer = transferRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransferNotFoundException::new);

        return toResponseScheme(transfer);
    }

    @Override
    public ResponseTransferScheme updateTransfer(Long userId, Long id, UpdateTransferScheme request) {
        Transfer transferToUpdate = transferRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransferNotFoundException::new);

        if (request.getFromAccountId() != null) {
            Account fromAccount = accountRepository.findByUserIdAndId(userId, request.getFromAccountId())
                    .orElseThrow(AccountNotFoundException::new);
            transferToUpdate.setFromAccount(fromAccount);
            transferToUpdate.setFromAccountId(fromAccount.getId());
        }

        if (request.getToAccountId() != null) {
            Account toAccount = accountRepository.findByUserIdAndId(userId, request.getToAccountId())
                    .orElseThrow(AccountNotFoundException::new);
            transferToUpdate.setFromAccount(toAccount);
            transferToUpdate.setFromAccountId(toAccount.getId());
        }

        if (request.getAmount() != null) transferToUpdate.setAmount(request.getAmount());
        if (request.getDescription() != null) transferToUpdate.setDescription(request.getDescription());
        if (request.getDate() != null) transferToUpdate.setDate(request.getDate());

        Transfer transfer = transferRepository.save(transferToUpdate);

        return toResponseScheme(transfer);
    }

    @Override
    public void deleteTransferById(Long userId, Long id) {
        transferRepository.findByUserIdAndId(userId, id)
                .ifPresentOrElse(transferRepository::delete, () -> {
                    throw new TransferNotFoundException();
                });
    }

    private ResponseTransferScheme toResponseScheme(Transfer transfer) {
        return ResponseTransferScheme.builder()
                .id(transfer.getId())
                .amount(transfer.getAmount())
                .description(transfer.getDescription())
                .date(transfer.getDate())
                .fromAccountId(transfer.getFromAccountId())
                .fromAccount(transfer.getFromAccount().getName())
                .toAccountId(transfer.getToAccountId())
                .toAccount(transfer.getToAccount().getName())
                .build();
    }
}
