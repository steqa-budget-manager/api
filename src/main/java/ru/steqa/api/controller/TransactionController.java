package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.model.User;
import ru.steqa.api.schema.transaction.AddTransactionSchema;
import ru.steqa.api.schema.transaction.ResponseTransactionSchema;
import ru.steqa.api.schema.transaction.UpdateTransactionSchema;
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
    public ResponseEntity<ResponseTransactionSchema> addTransaction(@RequestBody @Valid AddTransactionSchema request) {
        User user = authUtility.getCurrentUser();
        ResponseTransactionSchema transaction = transactionService.addTransaction(user.getId(), request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transaction.getId()).toUri();
        return ResponseEntity.created(location).body(transaction);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTransactionSchema>> getAllTransactions() {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(transactionService.getTransactions(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTransactionSchema> getTransactionById(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(transactionService.getTransactionById(user.getId(), id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTransactionSchema> updateTransaction(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionSchema request
    ) {
        User user = authUtility.getCurrentUser();
        ResponseTransactionSchema transaction = transactionService.updateTransaction(user.getId(), id, request);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        transactionService.deleteTransactionById(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
