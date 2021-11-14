package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Client;
import com.banksource.onlinebank.repos.ClientRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Component
@Transactional
public class ClientService implements clientServiceInterface{

    ClientRepo clientRepo;

    @Autowired
    public ClientService(ClientRepo clientRepo){
        this.clientRepo = clientRepo;
    }

    @Override
    public Client findByClientName(String name) {
        log.info("Found client by name {}", name);
        return clientRepo.findByClientName(name);
    }

    @Override
    public Client findById(Long id) {
        log.info("Found client by id {}", id);
        return clientRepo.getById(id);
    }
}
