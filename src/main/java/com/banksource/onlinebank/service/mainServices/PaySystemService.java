package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.PaySystem;
import com.banksource.onlinebank.repos.PaySystemRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Component
@Transactional
public class PaySystemService implements paySystemInterface{

    PaySystemRepo paySystemRepo;

    @Autowired
    public PaySystemService(PaySystemRepo paySystemRepo){
        this.paySystemRepo = paySystemRepo;
    }

    @Override
    public PaySystem findByType(String type) {
        log.info("Found pay system by type {}", type);
        return paySystemRepo.findByType(type);
    }

    @Override
    public PaySystem findById(Long id) {
        log.info("Found pay system by id {}", id);
        return paySystemRepo.getById(id);
    }
}
