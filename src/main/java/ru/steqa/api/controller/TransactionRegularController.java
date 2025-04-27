package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.steqa.api.scheme.transaction.regular.*;
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
    public ResponseEntity<List<ResponseTransactionRegularScheme>> getAllTransactionRegulars() {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transactionRegularService.getTransactionRegulars(userId));
    }

}
