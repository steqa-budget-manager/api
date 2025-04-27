package ru.steqa.api.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.steqa.api.grpc.GrpcClientService;
import ru.steqa.api.scheme.transaction.regular.*;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class RegularRuleUtility {
    private static final String FIXED_YEAR = "FIXED_YEAR";
    private static final String FIXED_MONTH = "FIXED_MONTH";
    private static final String INTERVAL_DAY = "INTERVAL_DAY";
    private static final String INTERVAL_SECOND = "INTERVAL_SECOND";

    private final GrpcClientService grpcClientService;

    public String addRegularRule(Long userId, Long transactionRegularId, AddRuleScheme rule) {
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        String mode = rule.getMode();

        switch (mode) {
            case FIXED_YEAR -> {
                FixedYearRuleScheme r = (FixedYearRuleScheme) rule;
                System.out.println(r.getDay());
                System.out.println(r.getMonth());
            }
            case FIXED_MONTH -> {
                FixedMonthRuleScheme r = (FixedMonthRuleScheme) rule;
                System.out.println(r.getDay());
            }
            case INTERVAL_DAY -> {
                IntervalDayRuleScheme r = (IntervalDayRuleScheme) rule;
                System.out.println(r.getDays());
            }
            case INTERVAL_SECOND -> {
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
}
