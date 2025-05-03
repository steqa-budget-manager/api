package ru.steqa.api.scheme.transaction;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.scheme.validators.ValidDate;
import ru.steqa.api.scheme.validators.ValidEnum;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class AddTransactionScheme {
    @NotNull
    @ValidEnum(enumClass = TransactionType.class)
    private String type;

    @NotNull
    @Min(0)
    @Max(100000000000000000L)
    private Long amount;

    @Length(max = 1000, message = "length must be less than or equal to 1000")
    private String description;

    @NotNull
    @ValidDate
    private String date;

    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private BigInteger accountId;

    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private BigInteger categoryId;

    public TransactionType getType() {
        return TransactionType.valueOf(type);
    }

    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public Long getAccountId() {
        return accountId.longValue();
    }

    public Long getCategoryId() {
        return categoryId.longValue();
    }

    @Builder
    public AddTransactionScheme(TransactionType type, Long amount, String description,
                                LocalDate date, Long accountId, Long categoryId) {
        this.type = type.name();
        this.amount = amount;
        this.description = description;
        this.date = date.toString();
        this.accountId = BigInteger.valueOf(accountId);
        this.categoryId = BigInteger.valueOf(categoryId);
    }
}
