package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Tariff;

public interface tariffServiceInterface {
    Tariff findByTariffName(String name);
    Tariff findById(Long id);
    Tariff findByTariffPercentage(float percentage);
}
