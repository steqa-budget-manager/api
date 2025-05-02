package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.scheme.transaction.AddTransactionScheme;
import ru.steqa.api.scheme.transaction.ResponseTransactionScheme;
import ru.steqa.api.scheme.transaction.TransactionFilter;
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
    @Operation(summary = "Add transaction")
    public ResponseEntity<ResponseTransactionScheme> addTransaction(@RequestBody @Valid AddTransactionScheme request) {
        Long userId = authUtility.getCurrentUserId();
        ResponseTransactionScheme transaction = transactionService.addTransaction(userId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transaction.getId()).toUri();
        return ResponseEntity.created(location).body(transaction);
    }

    @GetMapping
    @Operation(summary = "Get all transactions")
    public ResponseEntity<List<ResponseTransactionScheme>> getAllTransactions(TransactionFilter filter) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transactionService.getTransactions(userId, filter));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by id")
    public ResponseEntity<ResponseTransactionScheme> getTransactionById(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transactionService.getTransactionById(userId, id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update transaction")
    public ResponseEntity<ResponseTransactionScheme> updateTransaction(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionScheme request
    ) {
        Long userId = authUtility.getCurrentUserId();
        ResponseTransactionScheme transaction = transactionService.updateTransaction(userId, id, request);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete transaction")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        transactionService.deleteTransactionById(userId, id);
        return ResponseEntity.noContent().build();
    }
}
