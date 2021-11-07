package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionsRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOrderByAccountId(Long id);
    Transaction findByTransaction_group(String transaction_group);
    Transaction findByAccountId(Long id);
}
