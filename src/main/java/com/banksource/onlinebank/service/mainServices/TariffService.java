package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Tariff;
import com.banksource.onlinebank.repos.TariffRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Component
@Transactional
public class TariffService implements tariffServiceInterface{

    TariffRepo tariffRepo;

    @Autowired
    public TariffService(TariffRepo tariffRepo){
        this.tariffRepo = tariffRepo;
    }

    @Override
    public Tariff findByTariffName(String name) {
        log.info("Found tariff by tariff name {}", name);
        return tariffRepo.findByTariff_name(name);
    }

    @Override
    public Tariff findById(Long id) {
        log.info("Found tariff by tariff id {}", id);
        return tariffRepo.getById(id);
    }

    @Override
    public Tariff findByTariffPercentage(float percentage) {
        log.info("Found tariff by tariff percentage {}", percentage);
        return tariffRepo.findByTariff_percentage(percentage);
    }
}
