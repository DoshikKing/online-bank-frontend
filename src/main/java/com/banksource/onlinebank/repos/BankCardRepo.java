package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCardRepo extends JpaRepository<BankCard, Long>{
    BankCard findByCode(String code);
}
