package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByAccountNumber(String account_number);
    Account findByAccountName(String account_name);
    Account findByClientId(Long id);
    @Modifying
    @Query("update Account u set u.balance = ?1  where u.id = ?2")
    void setAccountInfoById(float summ, Long id);
}
