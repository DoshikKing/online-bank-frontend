package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BankCardRepo extends JpaRepository<BankCard, Long>{
    BankCard findByCode(String code);
    @Modifying
    @Query("update BankCard u set u.summ = ?1  where u.id = ?2")
    void setCardInfoById(float summ, Long id);
}
