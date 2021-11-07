package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.PaySystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaySystemRepo extends JpaRepository<PaySystem, Long> {
    PaySystem findByType(String type);
}
