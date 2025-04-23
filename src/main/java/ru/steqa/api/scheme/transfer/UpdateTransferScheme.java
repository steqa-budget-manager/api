package ru.steqa.api.scheme.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.steqa.api.scheme.validators.ValidDate;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
public class UpdateTransferScheme {
    @Min(0)
    @Max(Long.MAX_VALUE)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private BigInteger amount;

    @Length(max = 1000, message = "length must be less than or equal to 1000")
    private String description;

    @ValidDate
    private String date;

    @Min(0)
    @Max(Long.MAX_VALUE)
    private BigInteger fromAccountId;

    @Min(0)
    @Max(Long.MAX_VALUE)
    private BigInteger toAccountId;

    public Long getAmount() {
        if (amount == null) return null;
        return amount.longValue();
    }

    public LocalDate getDate() {
        if (date == null) return null;
        return LocalDate.parse(date);
    }

    public Long getFromAccountId() {
        if (fromAccountId == null) return null;
        return fromAccountId.longValue();
    }

    public Long getToAccountId() {
        if (toAccountId == null) return null;
        return toAccountId.longValue();
    }
}
