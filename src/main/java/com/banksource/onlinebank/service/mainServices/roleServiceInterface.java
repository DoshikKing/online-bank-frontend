package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Role;

public interface roleServiceInterface {
    Role findById(Long id);
    Role findByRole(String role);
}
