package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.scheme.transaction.category.AddTransactionCategoryScheme;
import ru.steqa.api.scheme.transaction.category.ResponseTransactionCategoryScheme;
import ru.steqa.api.scheme.transaction.category.UpdateTransactionCategoryScheme;
import ru.steqa.api.service.transaction.category.ITransactionCategoryService;
import ru.steqa.api.utility.AuthenticationUtility;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/transactions/categories")
@RequiredArgsConstructor
@Tag(name = "Transaction Category")
public class TransactionCategoryController {
    private final ITransactionCategoryService transactionCategoryService;
    private final AuthenticationUtility authUtility;

    @PostMapping
    @Operation(summary = "Add category")
    public ResponseEntity<ResponseTransactionCategoryScheme> addTransactionCategory(@RequestBody @Valid AddTransactionCategoryScheme request) {
        Long userId = authUtility.getCurrentUserId();
        ResponseTransactionCategoryScheme transactionCategory = transactionCategoryService.addTransactionCategory(userId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transactionCategory.getId()).toUri();
        return ResponseEntity.created(location).body(transactionCategory);
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<ResponseTransactionCategoryScheme>> getAllTransactionCategories() {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transactionCategoryService.getTransactionCategories(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
    public ResponseEntity<ResponseTransactionCategoryScheme> getTransactionCategoryById(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transactionCategoryService.getTransactionCategoryById(userId, id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update category")
    public ResponseEntity<ResponseTransactionCategoryScheme> updateTransactionCategory(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionCategoryScheme request
    ) {
        Long userId = authUtility.getCurrentUserId();
        ResponseTransactionCategoryScheme transactionCategory = transactionCategoryService.updateTransactionCategory(userId, id, request);
        return ResponseEntity.ok(transactionCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category")
    public ResponseEntity<Void> deleteTransactionCategory(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        transactionCategoryService.deleteTransactionCategoryById(userId, id);
        return ResponseEntity.noContent().build();
    }
}
