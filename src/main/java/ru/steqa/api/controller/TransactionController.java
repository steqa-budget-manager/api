package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.model.User;
import ru.steqa.api.scheme.transaction.AddTransactionScheme;
import ru.steqa.api.scheme.transaction.ResponseTransactionScheme;
import ru.steqa.api.scheme.transaction.UpdateTransactionScheme;
import ru.steqa.api.service.transaction.ITransactionService;
import ru.steqa.api.utility.AuthenticationUtility;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction")
public class TransactionController {
    private final ITransactionService transactionService;
    private final AuthenticationUtility authUtility;

    @PostMapping
    public ResponseEntity<ResponseTransactionScheme> addTransaction(@RequestBody @Valid AddTransactionScheme request) {
        User user = authUtility.getCurrentUser();
        ResponseTransactionScheme transaction = transactionService.addTransaction(user.getId(), request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transaction.getId()).toUri();
        return ResponseEntity.created(location).body(transaction);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTransactionScheme>> getAllTransactions() {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(transactionService.getTransactions(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTransactionScheme> getTransactionById(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(transactionService.getTransactionById(user.getId(), id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTransactionScheme> updateTransaction(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionScheme request
    ) {
        User user = authUtility.getCurrentUser();
        ResponseTransactionScheme transaction = transactionService.updateTransaction(user.getId(), id, request);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        transactionService.deleteTransactionById(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
