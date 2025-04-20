package ru.steqa.api.schema.transaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.schema.validators.ValidDate;
import ru.steqa.api.schema.validators.ValidEnum;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class UpdateTransactionSchema {
    @NotNull
    @ValidEnum(enumClass = TransactionType.class)
    private String type;

    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private BigInteger amount;

    @Length(max = 1000, message = "length must be less than or equal to 1000")
    private String description;

    @NotNull
    @ValidDate
    private String date;

    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private BigInteger userId;

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

    public Long getAmount() {
        return amount.longValue();
    }

    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public Long getUserId() {
        return userId.longValue();
    }

    public Long getAccountId() {
        return accountId.longValue();
    }

    public Long getCategoryId() {
        return categoryId.longValue();
    }
}
