package org.jabrimuhi.javacodetest.repository;

import org.jabrimuhi.javacodetest.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    @Modifying
    @Query("UPDATE Wallet w SET w.balance = w.balance + :amount WHERE w.id = :id")
    void deposit(@Param("id") UUID id, @Param("amount") Long amount);

    @Modifying
    @Query("UPDATE Wallet w SET w.balance = w.balance - :amount WHERE w.id = :id AND w.balance >= :amount")
    int withdraw(@Param("id") UUID id, @Param("amount") Long amount);

}
