package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepo extends JpaRepository<Tariff, Long> {
    Tariff findByTariffName(String name);
    Tariff findByTariffPercentage(float percentage);
}
