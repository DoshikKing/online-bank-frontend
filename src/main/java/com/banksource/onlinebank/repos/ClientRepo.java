package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByClientName(String client_name);
}
