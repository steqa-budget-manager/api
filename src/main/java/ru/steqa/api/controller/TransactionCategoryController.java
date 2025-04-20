package ru.steqa.api.controller;

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
@Tag(name = "Categories")
public class TransactionCategoryController {
    private final ITransactionCategoryService transactionCategoryService;
    private final AuthenticationUtility authUtility;

    @PostMapping
    public ResponseEntity<ResponseTransactionCategoryScheme> addCategory(@RequestBody @Valid AddTransactionCategoryScheme request) {
        User user = authUtility.getCurrentUser();
        ResponseTransactionCategoryScheme transactionCategory = transactionCategoryService.addTransactionCategory(user.getId(), request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transactionCategory.getId()).toUri();
        return ResponseEntity.created(location).body(transactionCategory);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTransactionCategoryScheme>> getAllCategories() {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(transactionCategoryService.getTransactionCategories(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTransactionCategoryScheme> getCategoryById(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(transactionCategoryService.getTransactionCategoryById(user.getId(), id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseTransactionCategoryScheme> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransactionCategoryScheme request
    ) {
        User user = authUtility.getCurrentUser();
        ResponseTransactionCategoryScheme transactionCategory = transactionCategoryService.updateTransactionCategory(user.getId(), id, request);
        return ResponseEntity.ok(transactionCategory);
    }

    @DeleteMapping("/{id}")
    @Description("")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        transactionCategoryService.deleteTransactionCategoryById(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
