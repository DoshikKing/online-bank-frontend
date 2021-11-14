package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Transaction;
import com.banksource.onlinebank.repos.TransactionsRepo;
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
public class TransactionService implements transactionServiceInterface{

    TransactionsRepo transactionsRepo;

    @Autowired
    public TransactionService(TransactionsRepo transactionsRepo){
        this.transactionsRepo = transactionsRepo;
    }

    @Override
    public void addTransaction(Transaction debitTransaction, Transaction creditTransaction) {
        log.info("Added new debit transaction {}", debitTransaction);
        log.info("Added new credit transaction {}", creditTransaction);
        transactionsRepo.save(debitTransaction);
        transactionsRepo.save(creditTransaction);
    }

    @Override
    public Transaction getTransactionByAccountId(Long id) {
        log.info("Found transaction by related account id");
        return transactionsRepo.findByAccountId(id);
    }

    @Override
    public Transaction getTransactionByTransactionGroup(String transaction_group) {
        log.info("Found transaction by transaction group code");
        return transactionsRepo.findByTransactionGroup(transaction_group);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        log.info("Found transaction by id");
        return transactionsRepo.getById(id);
    }

    @Override
    public void deleteTransactionById(Long id) {
        log.warn("Trying to delete transaction with id {}", id);
        try {
            log.info("Deleted transaction with id {} successfully", id);
            transactionsRepo.deleteById(id);
        } catch (Exception e) {
            log.error("Failed to delete transaction with id", id);
            throw new IllegalStateException("Cant delete it!");
        }
    }

    @Override
    public void deleteAllTransactions() {
        log.info("Deleted all transactions");
        transactionsRepo.deleteAll();
    }
}
