package ru.steqa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.steqa.api.scheme.transfer.AddTransferScheme;
import ru.steqa.api.scheme.transfer.ResponseTransferScheme;
import ru.steqa.api.scheme.transfer.UpdateTransferScheme;
import ru.steqa.api.service.transfer.ITransferService;
import ru.steqa.api.utility.AuthenticationUtility;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("${api.prefix}/transfers")
@RequiredArgsConstructor
@Tag(name = "Transfer")
public class TransferController {
    private final ITransferService transferService;
    private final AuthenticationUtility authUtility;

    @PostMapping
    @Operation(summary = "Add transfer")
    public ResponseEntity<ResponseTransferScheme> addTransfer(@RequestBody @Valid AddTransferScheme request) {
        Long userId = authUtility.getCurrentUserId();
        ResponseTransferScheme transfer = transferService.addTransfer(userId, request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transfer.getId()).toUri();
        return ResponseEntity.created(location).body(transfer);
    }

    @GetMapping
    @Operation(summary = "Get all transfers")
    public ResponseEntity<List<ResponseTransferScheme>> getAllTransfers() {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transferService.getTransfers(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transfer by id")
    public ResponseEntity<ResponseTransferScheme> getTransferById(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        return ResponseEntity.ok(transferService.getTransferById(userId, id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update transfer")
    public ResponseEntity<ResponseTransferScheme> updateTransfer(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTransferScheme request
    ) {
        Long userId = authUtility.getCurrentUserId();
        ResponseTransferScheme transfer = transferService.updateTransfer(userId, id, request);
        return ResponseEntity.ok(transfer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete transfer")
    public ResponseEntity<Void> deleteTransfer(@PathVariable Long id) {
        Long userId = authUtility.getCurrentUserId();
        transferService.deleteTransferById(userId, id);
        return ResponseEntity.noContent().build();
    }
}
