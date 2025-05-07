package ru.steqa.api.scheme.transfer;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ResponseTransferScheme {
    private Long id;
    private Long amount;
    private String description;
    private ZonedDateTime date;
    private Long fromAccountId;
    private String fromAccount;
    private Long toAccountId;
    private String toAccount;

    @Builder
    public ResponseTransferScheme(Long id, Long amount, String description,
                                  ZonedDateTime date, Long fromAccountId, String fromAccount, Long toAccountId, String toAccount) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.fromAccountId = fromAccountId;
        this.fromAccount = fromAccount;
        this.toAccountId = toAccountId;
        this.toAccount = toAccount;
    }
}
