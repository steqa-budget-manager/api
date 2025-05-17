package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.scheme.transaction.template.AddTransactionTemplateScheme;
import ru.steqa.api.scheme.transaction.template.ResponseTransactionTemplateScheme;
import ru.steqa.api.scheme.transaction.template.TransactionTemplateFilter;
import ru.steqa.api.scheme.transaction.template.UpdateTransactionTemplateScheme;
import ru.steqa.api.service.transaction.template.ITransactionTemplateService;
import ru.steqa.api.utility.AuthenticationUtility;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/transactions/templates")
@RequiredArgsConstructor
@Tag(name = "Transaction Template")
public class TransactionTemplateController {
    private final ITransactionTemplateService transactionTemplateService;
    private final AuthenticationUtility authUtility;

    @PostMapping
    @Operation(summary = "Add transaction template")
    public ResponseEntity<ResponseTransactionTemplateScheme> addTransactionTemplate(@RequestBody @Valid AddTransactionTemplateScheme request) {
        Long userId = authUtility.getCurrentUserId();
        ResponseTransactionTemplateScheme transactionTemplate = transactionTemplateService.addTransactionTemplate(userId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transactionTemplate.getId()).toUri();
        return ResponseEntity.created(location).body(transactionTemplate);
    }

    @GetMapping
    @Operation(summary = "Get all transaction templates")
    public ResponseEntity<List<ResponseTransactionTemplateScheme>> getAllTransactionTemplates(TransactionTemplateFilter filter) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transactionTemplateService.getTransactionTemplates(userId, filter));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction template by id")
    public ResponseEntity<ResponseTransactionTemplateScheme> getTransactionTemplateById(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transactionTemplateService.getTransactionTemplateById(userId, id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update transaction template")
    public ResponseEntity<ResponseTransactionTemplateScheme> updateTransactionTemplate(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionTemplateScheme request
    ) {
        Long userId = authUtility.getCurrentUserId();
        ResponseTransactionTemplateScheme transactionTemplate = transactionTemplateService.updateTransactionTemplate(userId, id, request);
        return ResponseEntity.ok(transactionTemplate);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete transaction template")
    public ResponseEntity<Void> deleteTransactionTemplate(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        transactionTemplateService.deleteTransactionTemplateById(userId, id);
        return ResponseEntity.noContent().build();
    }
}
