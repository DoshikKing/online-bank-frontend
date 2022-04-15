package com.banksource.onlinebank.work;

import com.banksource.onlinebank.components.CardTransaction;
import com.banksource.onlinebank.components.Transaction;
import com.banksource.onlinebank.data.TransactionData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AbstractOfTransactionsWrapper {

    private List<CardTransaction> cardTransactions;
    private List<Transaction> accountTransactions;

    public List<TransactionData> cardTransactionWrapper(List<CardTransaction> cardTransactions){
        this.cardTransactions = cardTransactions;
        List<TransactionData> transactionDataList = new ArrayList<>();

        for (CardTransaction cardTransaction :this.cardTransactions) {
            TransactionData transactionData = new TransactionData();

            transactionData.setComment(cardTransaction.getComment());
            transactionData.setIsDebit(cardTransaction.getIsDebit());
            transactionData.setSumm(cardTransaction.getSumm());
            transactionData.setTransactionTime(cardTransaction.getTransactionTime());
            transactionData.setCard_code(cardTransaction.getBankCard().getCode());
            transactionDataList.add(transactionData);
        }
        return transactionDataList;
    }

    public List<TransactionData> accountTransactionWrapper(List<Transaction> accountTransactions){
        this.accountTransactions = accountTransactions;
        List<TransactionData> transactionDataList = new ArrayList<>();

        for (Transaction accountTransaction :this.accountTransactions) {
            TransactionData transactionData = new TransactionData();
            transactionData.setComment(accountTransaction.getComment());
            transactionData.setIsDebit(accountTransaction.getIsDebit());
            transactionData.setSumm(accountTransaction.getSumm());
            transactionData.setTransactionTime(accountTransaction.getTransactionTime());
            transactionData.setCard_code(accountTransaction.getAccount().getAccountNumber());
            transactionDataList.add(transactionData);
        }
        return transactionDataList;
    }
}
