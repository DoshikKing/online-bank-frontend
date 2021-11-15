package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Account;

public interface accountServiceInterface {
    void addAccount(Account account);
    Account findByAccountName(String name);
    Account findByAccountNumber(String number);
    Account findByClientId(Long id);
    Account findById(Long id);
    void deleteAccountById(Long id);
    void deleteAllAccounts();
    void updateById(float summ, Long id);
}
