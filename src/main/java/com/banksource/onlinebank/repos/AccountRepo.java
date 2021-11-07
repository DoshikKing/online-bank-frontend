package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findByAccount_number(String account_number);
    Account findByAccount_name(String account_name);
    Account findByClientId(Long id);
}
