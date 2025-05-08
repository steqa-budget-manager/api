package ru.steqa.api.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.steqa.api.grpc.GrpcClientService;
import ru.steqa.api.scheme.transaction.regular.*;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@Component
public class RegularRuleUtility {
    private static final String FIXED_YEAR = "FIXED_YEAR";
    private static final String FIXED_MONTH = "FIXED_MONTH";
    private static final String INTERVAL_DAY = "INTERVAL_DAY";
    private static final String INTERVAL_SECOND = "INTERVAL_SECOND";

    private final GrpcClientService grpcClientService;

    private static final Integer timeOffset = 3;

    public String addRegularRule(Long userId, Long transactionRegularId, AddRuleScheme rule) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Moscow"));

        String mode = rule.getMode();

        switch (mode) {
            case FIXED_YEAR -> {
                FixedYearRuleScheme r = (FixedYearRuleScheme) rule;
                Integer month = r.getMonth();
                Integer day = r.getDay();
                now = now.withHour(timeOffset).withMinute(0).withSecond(0).withNano(0);
                LocalDateTime nextExecution;
                if (now.getMonthValue() <= month && now.getDayOfMonth() < day) {
                    nextExecution = now.withMonth(month).withDayOfMonth(day);
                } else {
                    nextExecution = now.plusYears(1).withMonth(month).withDayOfMonth(day);
                }
                return grpcClientService.addFixedYearRepetition(
                        userId,
                        transactionRegularId,
                        nextExecution,
                        month,
                        day
                );
            }
            case FIXED_MONTH -> {
                FixedMonthRuleScheme r = (FixedMonthRuleScheme) rule;
                Integer day = r.getDay();
                now = now.withHour(timeOffset).withMinute(0).withSecond(0).withNano(0);
                LocalDateTime nextExecution;
                if (now.getDayOfMonth() < day) {
                    nextExecution = now.withDayOfMonth(day);
                } else {
                    nextExecution = now.plusMonths(1).withDayOfMonth(day);
                }
                return grpcClientService.addFixedMonthRepetition(
                        userId,
                        transactionRegularId,
                        nextExecution,
                        day
                );
            }
            case INTERVAL_DAY -> {
                IntervalDayRuleScheme r = (IntervalDayRuleScheme) rule;
                now = now.withHour(timeOffset).withMinute(0).withSecond(0).withNano(0);
                return grpcClientService.addIntervalDayRepetition(
                        userId,
                        transactionRegularId,
                        now.plusDays(r.getDays()),
                        r.getDays()
                );
            }
            case INTERVAL_SECOND -> {
                now = LocalDateTime.now(Clock.systemUTC());
                IntervalSecondRuleScheme r = (IntervalSecondRuleScheme) rule;
                return grpcClientService.addIntervalSecondRepetition(
                        userId,
                        transactionRegularId,
                        now.plusSeconds(r.getSeconds()),
                        r.getSeconds()
                );
            }
        }
        return null;
    }

    public Boolean deleteRegularRule(String regularRuleId) {
        return grpcClientService.deleteRepetition(regularRuleId);
    }

    public String getShortName(AddRuleScheme rule) {
        String mode = rule.getMode();

        switch (mode) {
            case FIXED_YEAR -> {
                FixedYearRuleScheme r = (FixedYearRuleScheme) rule;
                Integer day = r.getDay();
                Integer month = r.getMonth();
                return "Каждый год " + day + " " + StringUtility.getMonthName(month);
            }
            case FIXED_MONTH -> {
                FixedMonthRuleScheme r = (FixedMonthRuleScheme) rule;
                Integer day = r.getDay();
                return "Каждый месяц " + day + " числа";
            }
            case INTERVAL_DAY -> {
                IntervalDayRuleScheme r = (IntervalDayRuleScheme) rule;
                Integer days = r.getDays();
                if (days == 1) return "Каждый день";
                return "Каждые " + days + " " + StringUtility.plural(Long.valueOf(days), "день", "дня", "дней");
            }
            case INTERVAL_SECOND -> {
                IntervalSecondRuleScheme r = (IntervalSecondRuleScheme) rule;
                Long seconds = r.getSeconds();
                if (seconds == 1) return "Каждую секунду";
                return "Каждые " + seconds + " " + StringUtility.plural(seconds, "секунда", "секунды", "секунд");
            }
        }
        return null;
    }
}
