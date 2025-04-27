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

        RepetitionRule repetitionRule = RepetitionRule.newBuilder()
                .setMode(Mode.INTERVAL_SECOND)
                .setTransactionType(Type.DEFAULT)
                .setTransactionId(transactionId)
                .setNextExecution(timeUtility.localDateTimeToInteger(nextExecution))
                .setUserId(userId)
                .setInterval(intervalSchedule)
                .build();

        AddRuleResponse response = blockingStub.addRule(repetitionRule);
        return response.getRuleId();
    }
}
