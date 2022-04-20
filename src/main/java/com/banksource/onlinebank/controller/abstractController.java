package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.Account;
import com.banksource.onlinebank.components.BankCard;
import com.banksource.onlinebank.data.TransactionData;
import com.banksource.onlinebank.service.mainServices.AccountService;
import com.banksource.onlinebank.service.mainServices.BankCardService;
import com.banksource.onlinebank.work.AbstractOfTransactionsWrapper;
import com.banksource.onlinebank.work.CheckAuthentication;
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
    private CheckAuthentication checkAuthentication;

    @Autowired
    public abstractController(BankCardService bankCardService, AccountService accountService,
                              AbstractOfTransactionsWrapper transactionWrapper, CheckAuthentication checkAuthentication){
        this.bankCardService = bankCardService;
        this.accountService = accountService;
        this.transactionWrapper = transactionWrapper;
        this.checkAuthentication = checkAuthentication;
    }

    @GetMapping("/card/{debit_id}")
    public ResponseEntity<List<TransactionData>> getCardAbstract(@PathVariable("debit_id") String debit_id, Authentication authentication){
        try {
            BankCard bankCard;

            if(!debit_id.isEmpty())
            {
                bankCard = bankCardService.getCardById(Long.parseLong(debit_id));

                return
                checkAuthentication.check(bankCard.getClient().getUser().getLogin(), authentication.getName())
                ? new ResponseEntity<>(transactionWrapper.cardTransactionWrapper(bankCard.getCardTransactionsList()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/account/{account_id}")
    public ResponseEntity<List<TransactionData>> getAccountAbstract(@PathVariable("account_id") String account_id, Authentication authentication){
        try{
            Account account;

            if(!account_id.isEmpty())
            {
                account = accountService.findById(Long.parseLong(account_id));

                return
                checkAuthentication.check(account.getClient().getUser().getLogin(),authentication.getName())
                ? new ResponseEntity<>(transactionWrapper.accountTransactionWrapper(account.getTransactions()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
