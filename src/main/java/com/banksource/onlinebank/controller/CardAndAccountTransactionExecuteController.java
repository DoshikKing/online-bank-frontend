package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.Account;
import com.banksource.onlinebank.components.BankCard;
import com.banksource.onlinebank.components.CardTransaction;
import com.banksource.onlinebank.components.Transaction;
import com.banksource.onlinebank.payload.request.data.ExecutableTransactionRequestData;
import com.banksource.onlinebank.payload.response.data.SimpleResponseData;
import com.banksource.onlinebank.service.mainServices.AccountService;
import com.banksource.onlinebank.service.mainServices.BankCardService;
import com.banksource.onlinebank.service.mainServices.CardTransactionService;
import com.banksource.onlinebank.service.mainServices.TransactionService;
import com.banksource.onlinebank.work.CheckAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import java.sql.Date;

@RestController
@RequestMapping(path="/api/pay", produces="application/json")
public class CardAndAccountTransactionExecuteController {

    private CardTransactionService cardTransactionService;
    private BankCardService bankCardService;
    private TransactionService transactionService;
    private AccountService accountService;
    private CheckAuthentication checkAuthentication;

    @Autowired
    public CardAndAccountTransactionExecuteController(CardTransactionService cardTransactionService,
                                                      BankCardService bankCardService, TransactionService transactionService,
                                                      AccountService accountService, CheckAuthentication checkAuthentication){
        this.cardTransactionService = cardTransactionService;
        this.bankCardService = bankCardService;
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.checkAuthentication = checkAuthentication;
    }


    @PostMapping("/with_card")
    public ResponseEntity<?> payment_with_card(@RequestBody ExecutableTransactionRequestData executableTransactionRequestData,
                          Authentication authentication){
        SimpleResponseData simpleResponseData = new SimpleResponseData();
        try{
            Long debit_id = executableTransactionRequestData.getDebit_id();
            Long credit_id = executableTransactionRequestData.getCredit_id();
            if(debit_id.equals(credit_id)){
                simpleResponseData.setComment("?????????????? ?????????????????? ???????????????? ???? ???? ???? ??????????! ???????????????? ?????? ?????????????????? ???? ?????????? ??????????!");
                return ResponseEntity.internalServerError().body(simpleResponseData);
            }
            float sum = executableTransactionRequestData.getAmount();
            String comment = executableTransactionRequestData.getComment();

            if (checkAuthentication.check(authentication.getName(), bankCardService.getCardById(debit_id).getClient().getUser().getLogin())){
                java.util.Date time = new java.util.Date();
                Date date = new Date(time.getTime());
                String uuid = UUID.randomUUID().toString();

                BankCard bankCard_debit = bankCardService.getCardById(debit_id);
                if(bankCard_debit.getSumm() - sum < 0){
                    simpleResponseData.setComment("???? ?????????????? ??????????????!");
                    return ResponseEntity.badRequest().body(simpleResponseData);
                }
                float percentage = bankCard_debit.getTariff().getTariffPercentage()/100;
                CardTransaction debitCardTransaction = new CardTransaction();
                debitCardTransaction.setBankCard(bankCard_debit);
                debitCardTransaction.setSumm(-(sum + (sum * percentage)));
                debitCardTransaction.setTransactionTime(date);
                debitCardTransaction.setIsDebit(true);
                debitCardTransaction.setTransactionGroup(uuid);
                debitCardTransaction.setComment(comment);

                BankCard bankCard_credit = bankCardService.getCardById(credit_id);
                CardTransaction creditCardTransaction = new CardTransaction();
                creditCardTransaction.setBankCard(bankCard_credit);
                creditCardTransaction.setSumm(sum);
                creditCardTransaction.setTransactionTime(date);
                creditCardTransaction.setIsDebit(false);
                creditCardTransaction.setTransactionGroup(uuid);
                creditCardTransaction.setComment(comment);

                cardTransactionService.addCardTransaction(debitCardTransaction, creditCardTransaction);

                bankCardService.updateById(bankCard_debit.getSumm() - (sum + (sum * percentage)), bankCard_debit.getId());
                bankCardService.updateById(bankCard_credit.getSumm() + sum, bankCard_credit.getId());
            } else {
                simpleResponseData.setComment("???????????????????????????? ????????????! ?????????????? ??????????????.");
                return ResponseEntity.internalServerError().body(simpleResponseData);
            }
            return ResponseEntity.ok(HttpStatus.OK);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            simpleResponseData.setComment("???????????????????????????? ????????????! ?????????????? ??????????????.");
            return ResponseEntity.internalServerError().body(simpleResponseData);
        }
    }

    @PostMapping("with_account")
    public ResponseEntity<?> payment_with_account(@RequestBody ExecutableTransactionRequestData executableTransactionRequestData,
                                  Authentication authentication){
        SimpleResponseData simpleResponseData = new SimpleResponseData();
        try {
            Long debit_id = executableTransactionRequestData.getDebit_id();
            Long credit_id = executableTransactionRequestData.getCredit_id();
            if(debit_id.equals(credit_id)){
                simpleResponseData.setComment("?????????????? ?????????????????? ???????????????? ???? ?????? ???? ????????! ???????????????? ?????? ?????????????????? ???? ?????????? ??????????!");
                return ResponseEntity.internalServerError().body(simpleResponseData);
            }
            float sum = executableTransactionRequestData.getAmount();
            String comment = executableTransactionRequestData.getComment();

            if (checkAuthentication.check(authentication.getName(), accountService.findById(debit_id).getClient().getUser().getLogin())){
                java.util.Date time = new java.util.Date();
                Date date = new Date(time.getTime());
                String uuid = UUID.randomUUID().toString();

                Account account_debit = accountService.findById(debit_id);
                if(account_debit.getBalance() - sum < 0){
                    simpleResponseData.setComment("???? ?????????????? ??????????????!");
                    return ResponseEntity.badRequest().body(simpleResponseData);
                }
                float percentage = account_debit.getTariff().getTariffPercentage()/100;
                Transaction debitTransaction = new Transaction();
                debitTransaction.setAccount(account_debit);
                debitTransaction.setSumm(-(sum + (sum * percentage)));
                debitTransaction.setTransactionTime(date);
                debitTransaction.setIsDebit(true);
                debitTransaction.setTransactionGroup(uuid);
                debitTransaction.setComment(comment);


                Account account_credit = accountService.findById(credit_id);

                Transaction creditTransaction = new Transaction();
                creditTransaction.setAccount(account_credit);
                creditTransaction.setSumm(sum);
                creditTransaction.setTransactionTime(date);
                creditTransaction.setIsDebit(false);
                creditTransaction.setTransactionGroup(uuid);
                creditTransaction.setComment(comment);

                transactionService.addTransaction(debitTransaction, creditTransaction);

                accountService.updateById(account_debit.getBalance() - (sum + (sum * percentage)), account_debit.getId());
                accountService.updateById(account_credit.getBalance() + sum, account_credit.getId());

            } else {
                simpleResponseData.setComment("???????????????????????????? ????????????! ?????????????? ??????????????.");
                return ResponseEntity.internalServerError().body(simpleResponseData);
            }
            return ResponseEntity.ok(HttpStatus.OK);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            simpleResponseData.setComment("???????????????????????????? ????????????! ?????????????? ??????????????.");
            return ResponseEntity.internalServerError().body(simpleResponseData);
        }
    }
}
