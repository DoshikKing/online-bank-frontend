package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardTransactionsRepo extends JpaRepository<CardTransaction, Long> {
    CardTransaction findByTransactionGroup(String id);
    CardTransaction findByBankCardId(Long id);
}
