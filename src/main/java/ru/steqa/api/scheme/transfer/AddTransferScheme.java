package ru.steqa.api.scheme.transfer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.steqa.api.scheme.validators.ValidDate;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class AddTransferScheme {
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
    private BigInteger fromAccountId;

    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private BigInteger toAccountId;

    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public Long getFromAccountId() {
        return fromAccountId.longValue();
    }

    public Long getToAccountId() {
        return toAccountId.longValue();
    }
}
