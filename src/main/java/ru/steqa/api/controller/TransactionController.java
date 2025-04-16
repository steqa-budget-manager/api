package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.schema.transaction.AddTransactionSchema;
import ru.steqa.api.schema.transaction.ResponseTransactionSchema;
import ru.steqa.api.schema.transaction.UpdateTransactionSchema;
import ru.steqa.api.service.transaction.ITransactionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction")
public class TransactionController {
    private final ITransactionService transactionService;

    @PostMapping
    public ResponseEntity<ResponseTransactionSchema> addTransaction(@RequestBody AddTransactionSchema request) {
        ResponseTransactionSchema transaction = transactionService.addTransaction(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transaction.getId()).toUri();
        return ResponseEntity.created(location).body(transaction);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTransactionSchema>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTransactionSchema> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTransactionSchema> updateTransaction(
            @RequestBody UpdateTransactionSchema request,
            @PathVariable Long id
    ) {
        ResponseTransactionSchema transaction = transactionService.updateTransaction(request, id);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransactionById(id);
        return ResponseEntity.noContent().build();
    }
}
