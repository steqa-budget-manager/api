package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.steqa.api.scheme.transaction.regular.AddTransactionRegularScheme;
import ru.steqa.api.scheme.transaction.regular.ResponseTransactionRegularScheme;
import ru.steqa.api.scheme.transaction.regular.TransactionRegularFilter;
import ru.steqa.api.service.transaction.regular.ITransactionRegularService;
import ru.steqa.api.utility.AuthenticationUtility;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/transactions/regulars")
@RequiredArgsConstructor
@Tag(name = "Transaction Regular")
public class TransactionRegularController {
    private final AuthenticationUtility authUtility;
    private final ITransactionRegularService transactionRegularService;

    @PostMapping
    @Operation(summary = "Add transaction regular")
    public ResponseEntity<ResponseTransactionRegularScheme> addRule(@RequestBody @Valid AddTransactionRegularScheme request) {
        Long userId = authUtility.getCurrentUserId();
        ResponseTransactionRegularScheme transactionRegular = transactionRegularService.addTransactionRegular(userId, request);
        return ResponseEntity.ok(transactionRegular);
    }

    @GetMapping
    @Operation(summary = "Get all transaction regulars")
    public ResponseEntity<List<ResponseTransactionRegularScheme>> getAllTransactionRegulars(TransactionRegularFilter filter) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transactionRegularService.getTransactionRegulars(userId, filter));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction regular by id")
    public ResponseEntity<ResponseTransactionRegularScheme> getTransactionRegularById(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transactionRegularService.getTransactionRegularById(userId, id));
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete transaction regular")
    public ResponseEntity<Void> deleteTransactionRegular(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        transactionRegularService.deleteTransactionRegularById(userId, id);
        return ResponseEntity.noContent().build();
    }
}
