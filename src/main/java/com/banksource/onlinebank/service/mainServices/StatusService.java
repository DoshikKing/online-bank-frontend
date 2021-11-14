package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Status;
import com.banksource.onlinebank.repos.StatusRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Component
@Transactional
public class StatusService implements statusServiceInterface{

    StatusRepo statusRepo;

    @Autowired
    public StatusService(StatusRepo statusRepo){
        this.statusRepo = statusRepo;
    }

    @Override
    public Status findByStatusName(String name) {
        log.info("Found status by name {}", name);
        return statusRepo.findByStatusName(name);
    }

    @Override
    public Status findById(Long id) {
        log.info("Found status by id {}", id);
        return statusRepo.getById(id);
    }
}
