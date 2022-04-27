package com.banksource.onlinebank.work;

import com.banksource.onlinebank.components.CardTransaction;
import com.banksource.onlinebank.components.Transaction;
import com.banksource.onlinebank.payload.response.data.TransactionData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AbstractOfTransactionsWrapper {

    public List<TransactionData> cardTransactionWrapper(List<CardTransaction> cardTransactions){
        List<TransactionData> transactionDataList = new ArrayList<>();

        for (CardTransaction cardTransaction :cardTransactions) {
            TransactionData transactionData = new TransactionData();

            transactionData.setComment(cardTransaction.getComment());
            transactionData.setIsDebit(cardTransaction.getIsDebit());
            transactionData.setSumm(cardTransaction.getSumm());
            transactionData.setTransactionTime(cardTransaction.getTransactionTime());
            transactionData.setCode(cardTransaction.getBankCard().getCode());
            transactionDataList.add(transactionData);
        }
        return transactionDataList;
    }

    public List<TransactionData> accountTransactionWrapper(List<Transaction> accountTransactions){
        List<TransactionData> transactionDataList = new ArrayList<>();

        for (Transaction accountTransaction :accountTransactions) {
            TransactionData transactionData = new TransactionData();
            transactionData.setComment(accountTransaction.getComment());
            transactionData.setIsDebit(accountTransaction.getIsDebit());
            transactionData.setSumm(accountTransaction.getSumm());
            transactionData.setTransactionTime(accountTransaction.getTransactionTime());
            transactionData.setCode(accountTransaction.getAccount().getAccountNumber());
            transactionDataList.add(transactionData);
        }
        return transactionDataList;
    }
}
