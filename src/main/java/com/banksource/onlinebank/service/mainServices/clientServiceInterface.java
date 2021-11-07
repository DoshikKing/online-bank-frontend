package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Client;

public interface clientServiceInterface {
    Client findByClientName(String name);
    Client findById(Long id);
}
