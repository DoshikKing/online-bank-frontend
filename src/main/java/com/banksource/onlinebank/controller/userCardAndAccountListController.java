package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.data.AccountData;
import com.banksource.onlinebank.data.CardData;
import com.banksource.onlinebank.service.mainServices.userService;
import com.banksource.onlinebank.work.AccountListWrapper;
import com.banksource.onlinebank.work.CardListWrapper;
import com.banksource.onlinebank.work.CheckAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/list", produces="application/json")
public class userCardAndAccountListController {

    private userService userService;
    private CardListWrapper cardListWrapper;
    private AccountListWrapper accountListWrapper;
    private CheckAuthentication checkAuthentication;

    @Autowired
    public userCardAndAccountListController(userService userService, CardListWrapper cardListWrapper,
                                            AccountListWrapper accountListWrapper, CheckAuthentication checkAuthentication){
        this.userService = userService;
        this.cardListWrapper = cardListWrapper;
        this.accountListWrapper = accountListWrapper;
        this.checkAuthentication = checkAuthentication;
    }

    @GetMapping("/cards")
    public ResponseEntity<List<CardData>> getCardsList(Authentication authentication){
        try {
            if (userService.findUser(authentication.getName()).getClient().getBankCard() !=  null){
                return
                checkAuthentication.check(userService.findUser(authentication.getName()).getLogin(), authentication.getName())
                ? new ResponseEntity<>(cardListWrapper.ListWrapper(userService.findUser(authentication.getName()).getClient().getBankCard()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountData>> getAccountsList(Authentication authentication){
        try {
            if (userService.findUser(authentication.getName()).getClient().getAccount() !=  null){
                return
                checkAuthentication.check(userService.findUser(authentication.getName()).getLogin(), authentication.getName())
                ? new ResponseEntity<>(accountListWrapper.ListWrapper(userService.findUser(authentication.getName()).getClient().getAccount()), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
