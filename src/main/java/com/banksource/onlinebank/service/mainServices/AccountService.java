package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Account;
import com.banksource.onlinebank.repos.AccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Component
@Transactional
public class AccountService implements accountServiceInterface{

    AccountRepo accountRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo){
        this.accountRepo = accountRepo;
    }

    @Override
    public void addAccount(Account account) {
        log.info("Added new account {}", account);
        accountRepo.save(account);
    }

    @Override
    public Account findByAccountName(String name) {
        log.info("Found account with name {}", name);
        return accountRepo.findByAccountName(name);
    }

    @Override
    public Account findByAccountNumber(String number) {
        log.info("Found account with number {}", number);
        return accountRepo.findByAccountNumber(number);
    }

    @Override
    public Account findByClientId(Long id) {
        log.info("Found account with client id {}", id);
        return accountRepo.findByClientId(id);
    }

    @Override
    public Account findById(Long id) {
        log.info("Found account with id {}", id);
        return accountRepo.getById(id);
    }

    @Override
    public void deleteAccountById(Long id) {
        log.warn("Trying to delete account with id {}", id);
        try {
            log.info("Deleted account with id {} successfully", id);
            accountRepo.deleteById(id);
        } catch (Exception e) {
            log.error("Failed to delete account with id", id);
            throw new IllegalStateException("Cant delete it!");
        }
    }

    @Override
    public void deleteAllAccounts() {
        log.info("Deleted all accounts");
        accountRepo.deleteAll();
    }
}
