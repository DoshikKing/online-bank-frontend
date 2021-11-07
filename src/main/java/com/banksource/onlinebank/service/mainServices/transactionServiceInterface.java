package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Transaction;

import java.util.List;

public interface transactionServiceInterface {
    void addTransaction(Transaction debitTransaction, Transaction creditTransaction);
    List<Transaction> filterByAccountId(Long id);
    Transaction getTransactionByAccountId(Long id);
    Transaction getTransactionByTransactionGroup(String transaction_group);
    Transaction getTransactionById(Long id);
    void deleteTransactionById(Long id);
    void deleteAllTransactions();
}
