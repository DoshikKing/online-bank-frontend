package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByClient_name(String client_name);
}
