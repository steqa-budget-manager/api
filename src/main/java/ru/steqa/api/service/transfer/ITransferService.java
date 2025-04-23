package ru.steqa.api.service.transfer;

import ru.steqa.api.scheme.transfer.AddTransferScheme;
import ru.steqa.api.scheme.transfer.ResponseTransferScheme;
import ru.steqa.api.scheme.transfer.UpdateTransferScheme;

import java.util.List;

public interface ITransferService {
    ResponseTransferScheme addTransfer(Long userId, AddTransferScheme transfer);
    List<ResponseTransferScheme> getTransfers(Long userId);
    ResponseTransferScheme getTransferById(Long userId, Long id);
    ResponseTransferScheme updateTransfer(Long userId, Long id, UpdateTransferScheme transfer);
    void deleteTransferById(Long userId, Long id);
}
