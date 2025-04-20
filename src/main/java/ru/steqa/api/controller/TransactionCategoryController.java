package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.model.User;
import ru.steqa.api.scheme.transaction.category.AddTransactionCategoryScheme;
import ru.steqa.api.scheme.transaction.category.ResponseTransactionCategoryScheme;
import ru.steqa.api.scheme.transaction.category.UpdateTransactionCategoryScheme;
import ru.steqa.api.service.transaction.category.ITransactionCategoryService;
import ru.steqa.api.utility.AuthenticationUtility;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
@Tag(name = "Category")
public class TransactionCategoryController {
    private final ITransactionCategoryService transactionCategoryService;
    private final AuthenticationUtility authUtility;

    @PostMapping
    @Operation(summary = "Add category")
    public ResponseEntity<ResponseTransactionCategoryScheme> addTransactionCategory(@RequestBody @Valid AddTransactionCategoryScheme request) {
        User user = authUtility.getCurrentUser();
        ResponseTransactionCategoryScheme transactionCategory = transactionCategoryService.addTransactionCategory(user.getId(), request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transactionCategory.getId()).toUri();
        return ResponseEntity.created(location).body(transactionCategory);
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<ResponseTransactionCategoryScheme>> getAllTransactionCategories() {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(transactionCategoryService.getTransactionCategories(user.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
    public ResponseEntity<ResponseTransactionCategoryScheme> getTransactionCategoryById(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(transactionCategoryService.getTransactionCategoryById(user.getId(), id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update category")
    public ResponseEntity<ResponseTransactionCategoryScheme> updateTransactionCategory(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionCategoryScheme request
    ) {
        User user = authUtility.getCurrentUser();
        ResponseTransactionCategoryScheme transactionCategory = transactionCategoryService.updateTransactionCategory(user.getId(), id, request);
        return ResponseEntity.ok(transactionCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category")
    public ResponseEntity<Void> deleteTransactionCategory(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        transactionCategoryService.deleteTransactionCategoryById(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
