package ru.steqa.api.service.transaction.regular;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.steqa.api.exception.account.AccountNotFoundException;
import ru.steqa.api.exception.transaction.TransactionNotFoundException;
import ru.steqa.api.exception.transaction.category.TransactionCategoryNotFoundException;
import ru.steqa.api.exception.transaction.regular.DeleteTransactionRegularException;
import ru.steqa.api.exception.user.UserNotFoundException;
import ru.steqa.api.model.*;
import ru.steqa.api.repository.*;
import ru.steqa.api.scheme.transaction.regular.AddTransactionRegularScheme;
import ru.steqa.api.scheme.transaction.regular.ResponseTransactionRegularScheme;
import ru.steqa.api.utility.RegularRuleUtility;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionRegularService implements ITransactionRegularService {
    private final IUserRepository userRepository;
    private final IAccountRepository accountRepository;
    private final ITransactionCategoryRepository transactionCategoryRepository;
    private final ITransactionRegularRepository transactionRegularRepository;
    private final RegularRuleUtility regularRuleUtility;

    @Override
    public ResponseTransactionRegularScheme addTransactionRegular(Long userId, AddTransactionRegularScheme request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Account account = accountRepository.findByUserIdAndId(userId, request.getAccountId())
                .orElseThrow(AccountNotFoundException::new);

        TransactionCategory category = transactionCategoryRepository.findByUserIdAndId(userId, request.getCategoryId())
                .orElseThrow(TransactionCategoryNotFoundException::new);

        TransactionRegular transactionRegularToAdd = TransactionRegular.builder()
                .type(request.getType())
                .amount(request.getAmount())
                .description(request.getDescription())
                .user(user)
                .account(account)
                .category(category)
                .build();
        TransactionRegular transactionRegular = transactionRegularRepository.save(transactionRegularToAdd);

        String repetitionRuleId = regularRuleUtility.addRegularRule(userId, transactionRegularToAdd.getId(), request.getRule());

        transactionRegular.setRepetitionRuleId(repetitionRuleId);
        transactionRegular = transactionRegularRepository.save(transactionRegularToAdd);

        return ResponseTransactionRegularScheme.builder()
                .id(transactionRegular.getId())
                .type(transactionRegular.getType())
                .amount(transactionRegular.getAmount())
                .description(transactionRegular.getDescription())
                .rule(request.getRule())
                .accountId(transactionRegular.getAccountId())
                .categoryId(transactionRegular.getCategoryId())
                .build();
    }

    @Override
    public List<ResponseTransactionRegularScheme> getTransactionRegulars(Long userId) {
        return List.of();
    }

    @Override
    public void deleteTransactionRegularById(Long userId, Long id) {
        TransactionRegular transactionRegular = transactionRegularRepository.findByUserIdAndId(userId, id)
                .orElseThrow(TransactionNotFoundException::new);

        Boolean deleted = regularRuleUtility.deleteRegularRule(transactionRegular.getRepetitionRuleId());

        if (deleted) {
            transactionRegularRepository.delete(transactionRegular);
        } else {
            throw new DeleteTransactionRegularException();
        }
    }
}
