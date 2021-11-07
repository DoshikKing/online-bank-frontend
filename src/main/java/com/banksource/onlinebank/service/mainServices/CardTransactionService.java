package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.CardTransaction;
import com.banksource.onlinebank.repos.CardTransactionsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Component
@Transactional
public class CardTransactionService implements cardTransactionsServiceInterface{

    CardTransactionsRepo cardTransactionsRepo;

    @Autowired
    public CardTransactionService(CardTransactionsRepo cardTransactionsRepo){
        this.cardTransactionsRepo = cardTransactionsRepo;
    }

    @Override
    public void addCardTransaction(CardTransaction debitCardTransaction, CardTransaction creditCardTransaction) {
        log.info("Added new debit transaction for card {}", debitCardTransaction);
        log.info("Added new credit transaction for card {}", creditCardTransaction);
        cardTransactionsRepo.save(debitCardTransaction);
        cardTransactionsRepo.save(creditCardTransaction);
    }

    @Override
    public List<CardTransaction> filterByBankCardId(Long id) {
        log.info("Found all transactions by bank card id {}", id);
        return cardTransactionsRepo.findByOrderByBankCardId(id);
    }

    @Override
    public CardTransaction findById(Long id) {
        log.info("Found card transaction with id {}", id);
        return cardTransactionsRepo.getById(id);
    }

    @Override
    public CardTransaction findByBankCardId(Long id) {
        log.info("Found card transaction by bank card id {}", id);
        return cardTransactionsRepo.findByBankCardId(id);
    }

    @Override
    public CardTransaction findByTransactionGroup(String transaction_group) {
        log.info("Found card transaction by transaction group code {}", transaction_group);
        return cardTransactionsRepo.findByTransaction_group(transaction_group);
    }

    @Override
    public void deleteCardTransactionById(Long id) {
        log.warn("Trying to delete card transaction with id {}", id);
        try {
            log.info("Deleted card transaction with id {} successfully", id);
            cardTransactionsRepo.deleteById(id);
        } catch (Exception e) {
            log.error("Failed to delete transaction with id", id);
            throw new IllegalStateException("Cant delete it!");
        }
    }

    @Override
    public void deleteAllCardTransactions() {
        log.info("Deleted all card transactions");
        cardTransactionsRepo.deleteAll();
    }
}
