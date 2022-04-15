package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.Account;
import com.banksource.onlinebank.components.BankCard;
import com.banksource.onlinebank.data.TransactionData;
import com.banksource.onlinebank.service.mainServices.AccountService;
import com.banksource.onlinebank.service.mainServices.BankCardService;
import com.banksource.onlinebank.work.AbstractOfTransactionsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/abstract", produces="application/json")
public class abstractController {

    private BankCardService bankCardService;
    private AccountService accountService;
    private AbstractOfTransactionsWrapper transactionWrapper;

    @Autowired
    public abstractController(BankCardService bankCardService, AccountService accountService, AbstractOfTransactionsWrapper transactionWrapper){
        this.bankCardService = bankCardService;
        this.accountService = accountService;
        this.transactionWrapper = transactionWrapper;
    }

    @GetMapping("/card/{debit_id}")
    public ResponseEntity<List<TransactionData>> getCardAbstract(@PathVariable("debit_id") String debit_id, Authentication authentication){
        BankCard bankCard;
        if(!debit_id.isEmpty())
        {
            bankCard = bankCardService.getCardById(Long.parseLong(debit_id));
            if (bankCard.getClient().getUser().getLogin().equals(authentication.getName()))
            {
                return new ResponseEntity<>(transactionWrapper.cardTransactionWrapper(bankCard.getCardTransactionsList()), HttpStatus.OK) ;
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/account/{debit_id}")
    public ResponseEntity<List<TransactionData>> getAccountAbstract(@PathVariable("debit_id") String debit_id, Authentication authentication){
        Account account;
        if(!debit_id.isEmpty())
        {
            account = accountService.findById(Long.parseLong(debit_id));
            if (account.getClient().getUser().getLogin().equals(authentication.getName()))
            {
                return new ResponseEntity<>(transactionWrapper.accountTransactionWrapper(account.getTransactions()), HttpStatus.OK) ;
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
