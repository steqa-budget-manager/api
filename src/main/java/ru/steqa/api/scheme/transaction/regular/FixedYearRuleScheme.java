package ru.steqa.api.scheme.transaction.regular;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FixedYearRuleScheme extends AddRuleScheme {
    @NotNull
    @Min(1)
    @Max(28)
    private Integer day;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer month;
}
