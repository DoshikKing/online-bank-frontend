package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.BankCard;
import com.banksource.onlinebank.repos.BankCardRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Component
@Transactional
public class BankCardService implements bankCardServiceInterface{
    BankCardRepo bankCardRepo;

    @Autowired
    public BankCardService(BankCardRepo bankCardRepo){
        this.bankCardRepo = bankCardRepo;
    }

    @Override
    public void addCard(BankCard bankCard) {
        log.info("Added new bank card {}", bankCard);
        bankCardRepo.save(bankCard);
    }

    @Override
    public BankCard getCardById(Long id) {
        log.info("Found bank card by id {}", id);
        return bankCardRepo.getById(id);
    }

    @Override
    public BankCard getCardByCode(String code) {
        log.info("Found bank card by code {}", code);
        return bankCardRepo.findByCode(code);
    }

    @Override
    public void deleteCardById(Long id) {
        log.warn("Trying to delete card with id {}", id);
        try {
            log.info("Deleted card with id {} successfully", id);
            bankCardRepo.deleteById(id);
        } catch (Exception e) {
            log.error("Failed to delete card with id", id);
            throw new IllegalStateException("Cant delete it!");
        }
    }

    @Override
    public void deleteAllCards() {
        log.info("Deleted all cards");
        bankCardRepo.deleteAll();
    }

    @Override
    public void updateById(float summ, Long id) {
        bankCardRepo.setCardInfoById(summ, id);
    }
}
