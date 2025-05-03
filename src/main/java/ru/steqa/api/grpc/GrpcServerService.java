package ru.steqa.api.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.steqa.api.model.TransactionRegular;
import ru.steqa.api.repository.ITransactionRegularRepository;
import ru.steqa.api.scheme.transaction.AddTransactionScheme;
import ru.steqa.api.service.transaction.ITransactionService;
import ru.steqa.grpc.AddTransactionRequest;
import ru.steqa.grpc.AddTransactionResponse;
import ru.steqa.grpc.ApiServiceGrpc;

import java.time.ZonedDateTime;

@GrpcService
@RequiredArgsConstructor
public class GrpcServerService extends ApiServiceGrpc.ApiServiceImplBase {
    private final ITransactionRegularRepository transactionRegularRepository;
    private final ITransactionService transactionService;

    @Override
    public void addTransaction(AddTransactionRequest request, StreamObserver<AddTransactionResponse> responseObserver) {
        TransactionRegular transactionRegular = transactionRegularRepository.findById(request.getTransactionId()).orElse(null);
        AddTransactionScheme transactionToAdd = AddTransactionScheme.builder()
                .type(transactionRegular.getType())
                .amount(transactionRegular.getAmount())
                .description(transactionRegular.getDescription())
                .date(ZonedDateTime.now())
                .accountId(transactionRegular.getAccountId())
                .categoryId(transactionRegular.getCategoryId())
                .build();
        transactionService.addTransaction(request.getUserId(), transactionToAdd);

        AddTransactionResponse response = AddTransactionResponse.newBuilder()
                .setStatus(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
