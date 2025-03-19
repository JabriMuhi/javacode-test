package org.jabrimuhi.javacodetest.service;

import org.jabrimuhi.javacodetest.model.OperationType;

import java.util.UUID;

public interface WalletService {
    void updateBalance (UUID walletId, Long amount, OperationType operationType);
    Long getBalance(UUID walletId);
}
