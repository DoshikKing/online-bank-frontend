package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.CardTransaction;

import java.util.List;

public interface cardTransactionsServiceInterface {
    void addCardTransaction(CardTransaction debitCardTransaction, CardTransaction creditCardTransaction);
    List<CardTransaction> filterByBankCardId(Long id);
    CardTransaction findById(Long id);
    CardTransaction findByBankCardId(Long id);
    CardTransaction findByTransactionGroup(String transaction_group);
    void deleteCardTransactionById(Long id);
    void deleteAllCardTransactions();
}
