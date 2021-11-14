package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepo extends JpaRepository<Transaction, Long> {
    Transaction findByTransactionGroup(String transaction_group);
    Transaction findByAccountId(Long id);
}
