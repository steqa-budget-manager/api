package ru.steqa.api.utility;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TimeUtility {
    public Integer localDateTimeToInteger(LocalDateTime dateTime) {
        long secondsSinceEpoch = dateTime.toEpochSecond(ZoneOffset.UTC);
        return (int) (secondsSinceEpoch % Integer.MAX_VALUE);
    }
}
