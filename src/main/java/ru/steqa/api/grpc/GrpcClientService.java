package ru.steqa.api.grpc;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.steqa.api.utility.TimeUtility;
import ru.steqa.grpc.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GrpcClientService {
    @GrpcClient("service")
    private RepetitionServiceGrpc.RepetitionServiceBlockingStub blockingStub;

    private final TimeUtility timeUtility;

    public String addIntervalSecondRepetition(Long userId, Long transactionId, LocalDateTime nextExecution, Long interval) {
        IntervalSchedule intervalSchedule = IntervalSchedule.newBuilder()
                .setSeconds(interval)
                .build();

        AddRuleRequest request = AddRuleRequest.newBuilder()
                .setMode(Mode.INTERVAL_SECOND)
                .setTransactionType(Type.DEFAULT)
                .setTransactionId(transactionId)
                .setNextExecution(timeUtility.localDateTimeToInteger(nextExecution))
                .setUserId(userId)
                .setInterval(intervalSchedule)
                .build();

        RuleIdResponse response = blockingStub.addRule(request);
        return response.getRuleId();
    }

    public String addIntervalDayRepetition(Long userId, Long transactionId, LocalDateTime nextExecution, Integer interval) {
        IntervalSchedule intervalSchedule = IntervalSchedule.newBuilder()
                .setDays(interval)
                .build();

        AddRuleRequest request = AddRuleRequest.newBuilder()
                .setMode(Mode.INTERVAL_DAY)
                .setTransactionType(Type.DEFAULT)
                .setTransactionId(transactionId)
                .setNextExecution(timeUtility.localDateTimeToInteger(nextExecution))
                .setUserId(userId)
                .setInterval(intervalSchedule)
                .build();

        RuleIdResponse response = blockingStub.addRule(request);
        return response.getRuleId();
    }

    public String addFixedMonthRepetition(Long userId, Long transactionId, LocalDateTime nextExecution, Integer day) {
        FixedSchedule fixedSchedule = FixedSchedule.newBuilder()
                .setDay(day)
                .build();

        AddRuleRequest request = AddRuleRequest.newBuilder()
                .setMode(Mode.FIXED_MONTH)
                .setTransactionType(Type.DEFAULT)
                .setTransactionId(transactionId)
                .setNextExecution(timeUtility.localDateTimeToInteger(nextExecution))
                .setUserId(userId)
                .setFixed(fixedSchedule)
                .build();

        RuleIdResponse response = blockingStub.addRule(request);
        return response.getRuleId();
    }

    public Boolean deleteRepetition(String ruleId) {
        DeleteRuleRequest request = DeleteRuleRequest.newBuilder()
                .setRuleId(ruleId)
                .build();

        StatusResponse response = blockingStub.deleteRule(request);
        return response.getStatus();
    }
}
