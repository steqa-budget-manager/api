package ru.steqa.api.scheme.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private BigInteger fromAccountId;

    @NotNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    private BigInteger toAccountId;

    public Long getAmount() {
        return amount.longValue();
    }

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
