package com.banksource.onlinebank.work;

import com.banksource.onlinebank.components.Account;
import com.banksource.onlinebank.data.AccountData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountListWrapper {

    public List<AccountData> ListWrapper(List<Account> accountList){
        List<AccountData> accountDataList = new ArrayList<>();
        for (Account account: accountList) {
            AccountData accountData = new AccountData();

            accountData.setAccountNumber(account.getAccountNumber());
            accountData.setBalance(account.getBalance());
            accountData.setStatusTime(account.getStatusTime());

            accountDataList.add(accountData);
        }
        return accountDataList;
    }
}
