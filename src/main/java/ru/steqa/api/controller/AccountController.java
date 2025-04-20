package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.model.User;
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
    public ResponseEntity<ResponseAccountScheme> addAccount(@RequestBody @Valid AddAccountScheme request) {
        User user = authUtility.getCurrentUser();
        ResponseAccountScheme account = accountService.addAccount(user.getId(), request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(account.getId()).toUri();
        return ResponseEntity.created(location).body(account);
    }

    @GetMapping
    public ResponseEntity<List<ResponseAccountScheme>> getAllAccounts() {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(accountService.getAccounts(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseAccountScheme> getAccountById(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        return ResponseEntity.ok(accountService.getAccountById(user.getId(), id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseAccountScheme> updateAccount(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAccountScheme request
    ) {
        User user = authUtility.getCurrentUser();
        ResponseAccountScheme account = accountService.updateAccount(user.getId(), id, request);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    @Description("")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        User user = authUtility.getCurrentUser();
        accountService.deleteAccountById(user.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
