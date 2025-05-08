package ru.steqa.api.scheme.transaction.regular;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IntervalSecondRuleScheme extends AddRuleScheme {
    @NotNull
    @Min(1)
    private Long seconds;
}
