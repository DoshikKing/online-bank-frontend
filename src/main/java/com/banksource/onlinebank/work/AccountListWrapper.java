package com.banksource.onlinebank.work;

import com.banksource.onlinebank.components.Account;
import com.banksource.onlinebank.payload.response.data.AccountData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountListWrapper {

    public List<AccountData> ListWrapper(List<Account> accountList){
        List<AccountData> accountDataList = new ArrayList<>();
        for (Account account: accountList) {
            AccountData accountData = new AccountData();

            accountData.setId(account.getId());
            accountData.setAccountNumber(account.getAccountNumber());
            accountData.setBalance(account.getBalance());
            accountData.setStatusTime(account.getStatusTime());

            accountDataList.add(accountData);
        }
        return accountDataList;
    }
}
