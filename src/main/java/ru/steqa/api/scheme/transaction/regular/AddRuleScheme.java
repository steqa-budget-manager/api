package ru.steqa.api.scheme.transaction.regular;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "mode",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixedYearRuleScheme.class, name = "FIXED_YEAR"),
        @JsonSubTypes.Type(value = FixedMonthRuleScheme.class, name = "FIXED_MONTH"),
        @JsonSubTypes.Type(value = IntervalDayRuleScheme.class, name = "INTERVAL_DAY"),
        @JsonSubTypes.Type(value = IntervalSecondRuleScheme.class, name = "INTERVAL_SECOND")
})
@Data
public abstract class AddRuleScheme {
    @NotNull @Pattern(regexp = "DEFAULT|DEPOSIT")
    private String transactionType;
    @NotNull
    private String mode;
}

