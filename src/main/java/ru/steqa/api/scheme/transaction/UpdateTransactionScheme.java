package ru.steqa.api.scheme.transaction;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.scheme.validators.ValidDate;
import ru.steqa.api.scheme.validators.ValidEnum;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class UpdateTransactionScheme {
    @ValidEnum(enumClass = TransactionType.class)
    private String type;

    @Min(0)
    @Max(100000000000000000L)
    private Long amount;

    @Length(max = 1000, message = "length must be less than or equal to 1000")
    private String description;

    @ValidDate
    private String date;

    @Min(0)
    @Max(Long.MAX_VALUE)
    private BigInteger accountId;

    @Min(0)
    @Max(Long.MAX_VALUE)
    private BigInteger categoryId;

    public TransactionType getType() {
        if (type == null) return null;
        return TransactionType.valueOf(type);
    }

    public Long getAmount() {
        if (amount == null) return null;
        return amount;
    }

    public LocalDate getDate() {
        if (date == null) return null;
        return LocalDate.parse(date);
    }

    public Long getAccountId() {
        if (accountId == null) return null;
        return accountId.longValue();
    }

    public Long getCategoryId() {
        if (categoryId == null) return null;
        return categoryId.longValue();
    }
}
