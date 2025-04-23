package ru.steqa.api.scheme.transfer;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResponseTransferScheme {
    private Long id;
    private Long amount;
    private String description;
    private LocalDate date;
    private Long fromAccountId;
    private Long toAccountId;

    @Builder
    public ResponseTransferScheme(Long id, Long amount, String description,
                                     LocalDate date, Long fromAccountId, Long toAccountId) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }
}
