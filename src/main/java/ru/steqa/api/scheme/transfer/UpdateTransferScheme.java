package ru.steqa.api.scheme.transfer;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.steqa.api.scheme.validators.ValidDate;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@Data
public class UpdateTransferScheme {
    @Min(0)
    @Max(100000000000000000L)
    private Long amount;

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
        return amount;
    }

    public ZonedDateTime getDate() {
        if (date == null) return null;
        return ZonedDateTime.parse(date);
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
