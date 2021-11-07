package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankCardRepo extends JpaRepository<BankCard, Long>{
    List<BankCard> findByOrderByClientId(Long id);
    BankCard findByCode(String code);
}
