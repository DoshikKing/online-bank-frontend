package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepo extends JpaRepository<Status, Long> {
    Status findByStatus_name(String name);
}
