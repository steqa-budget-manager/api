package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.dto.AccountBalanceDto;
import ru.steqa.api.scheme.account.AccountFilter;
import ru.steqa.api.scheme.account.AddAccountScheme;
import ru.steqa.api.scheme.account.ResponseAccountScheme;
import ru.steqa.api.scheme.account.UpdateAccountScheme;
import ru.steqa.api.service.account.IAccountService;
import ru.steqa.api.utility.AuthenticationUtility;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/accounts")
@RequiredArgsConstructor
@Tag(name = "Account")
public class AccountController {
    private final IAccountService accountService;
    private final AuthenticationUtility authUtility;

    @PostMapping
    @Operation(summary = "Add account")
    public ResponseEntity<ResponseAccountScheme> addAccount(@RequestBody @Valid AddAccountScheme request) {
        Long userId = authUtility.getCurrentUserId();
        ResponseAccountScheme account = accountService.addAccount(userId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(location).body(account);
    }

    @GetMapping
    @Operation(summary = "Get all accounts")
    public ResponseEntity<List<ResponseAccountScheme>> getAllAccounts(AccountFilter filter) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(accountService.getAccounts(userId, filter));
    }

    @GetMapping("/balances")
    @Operation(summary = "Get account balances")
    public ResponseEntity<List<AccountBalanceDto>> getAccountBalances(AccountFilter filter) {
        Long userId = authUtility.getCurrentUserId();
        Boolean visible = filter.getVisible();
        System.out.println(visible);
        return ResponseEntity.ok(accountService.getAccountBalances(userId, visible));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get account by id")
    public ResponseEntity<ResponseAccountScheme> getAccountById(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(accountService.getAccountById(userId, id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update account")
    public ResponseEntity<ResponseAccountScheme> updateAccount(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAccountScheme request
    ) {
        Long userId = authUtility.getCurrentUserId();
        ResponseAccountScheme account = accountService.updateAccount(userId, id, request);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete account")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        accountService.deleteAccountById(userId, id);
        return ResponseEntity.noContent().build();
    }
}
