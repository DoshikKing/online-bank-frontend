package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Status;

public interface statusServiceInterface {
    Status findByStatusName(String name);
    Status findById(Long id);
}
