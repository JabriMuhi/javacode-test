package org.jabrimuhi.javacodetest.service.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jabrimuhi.javacodetest.exception.InsufficientFundsException;
import org.jabrimuhi.javacodetest.exception.InvalidOperationTypeException;
import org.jabrimuhi.javacodetest.exception.WalletNotFoundException;
import org.jabrimuhi.javacodetest.model.OperationType;
import org.jabrimuhi.javacodetest.model.Wallet;
import org.jabrimuhi.javacodetest.repository.WalletRepository;
import org.jabrimuhi.javacodetest.service.WalletService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;

    @Override
    @Transactional
    public void updateBalance(UUID walletId, Long amount, OperationType operationType) {
          if (!walletRepository.existsById(walletId)) {
            throw new WalletNotFoundException("Wallet not found");
        }

        if (operationType == OperationType.DEPOSIT) {
            walletRepository.deposit(walletId, amount);
        } else if (operationType == OperationType.WITHDRAW) {
            int affectedRows = walletRepository.withdraw(walletId, amount);
            if (affectedRows == 0) {
                throw new InsufficientFundsException("Insufficient funds");
            }
        } else {
            throw new InvalidOperationTypeException("Invalid operation type");
        }
    }

    @Override
    public Long getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        return wallet.getBalance();
    }
}
