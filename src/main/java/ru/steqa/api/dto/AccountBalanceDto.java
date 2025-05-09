package ru.steqa.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public interface AccountBalanceDto {
    Long getId();
    Long getUserId();
    String getName();
    Boolean getVisible();
    Instant getCreatedAt();
    BigDecimal getBalance();
}