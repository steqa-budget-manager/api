package ru.steqa.api.scheme.transaction.regular;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.steqa.api.model.TransactionType;
import ru.steqa.api.scheme.validators.ValidEnum;

import java.math.BigInteger;

@Data
public class AddTransactionRegularScheme {
    @NotNull
    @ValidEnum(enumClass = TransactionType.class)
    private String type;

    @NotNull
    @Length(min = 1, max = 255)
    private String name;

    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private BigInteger amount;

    @Length(max = 1000, message = "length must be less than or equal to 1000")
    private String description;

    private AddRuleScheme rule;

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

    public Long getAccountId() {
        return accountId.longValue();
    }

    public Long getCategoryId() {
        return categoryId.longValue();
    }
}
