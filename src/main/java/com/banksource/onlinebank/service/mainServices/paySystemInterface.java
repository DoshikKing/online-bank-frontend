package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.PaySystem;

public interface paySystemInterface {
    PaySystem findByType(String type);
    PaySystem findById(Long id);
}
