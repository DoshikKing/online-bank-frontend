package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Shop;
import com.banksource.onlinebank.repos.ShopRepo;
import com.banksource.onlinebank.service.emailService.emailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Component
@Transactional
@Deprecated
public class ShopService implements shopServiceInterface {
    ShopRepo shopRepo;
    emailService emailService;

    @Autowired
    public ShopService(ShopRepo shopRepo, emailService emailService){
        this.emailService = emailService;
        this.shopRepo = shopRepo;
    }

    public ShopService(ShopRepo shopRepo) {
        this.shopRepo = shopRepo;
    }

    @Override
    public void addShop(Shop shop) {
        log.info("Added new bank {}", shop);
        //email service
        //emailService.sendMessageAboutBank(bank);
        shopRepo.save(shop);
    }

    @Override
    public List<Shop> getAllShops() {
        log.info("Found all shops");
        List<Shop> shopList = shopRepo.findAll();
        return shopList;
    }

    @Override
    public List<Shop> filterByName() {
        log.info("Sorted list of shops by name");
        return shopRepo.findByOrderByName();
    }

    @Override
    public List<Shop> filterByAddress() {
        log.info("Sorted list of shops by address");
        return shopRepo.findByOrderByAddress();
    }

    @Override
    public List<Shop> filterByShopId() {
        log.info("Sorted list of shops by id");
        return shopRepo.findByOrderById();
    }

    @Override
    public Shop getShopById(Long id) {
        log.info("Found shop by id {}", id);
        return shopRepo.getById(id);
    }

    @Override
    public void deleteShopById(Long id) {
        log.warn("Trying to delete shop with id {}", id);
        try {
            log.info("Deleted shop with id {} successfully", id);
            shopRepo.deleteById(id);
        } catch (Exception e) {
            log.error("Failed to delete shop with id", id);
            throw new IllegalStateException("Shop has linked records! You cant remove it!");
        }
    }

    @Override
    public void deleteAllShops() {
        log.info("Deleted all shops");
        shopRepo.deleteAll();
    }

    @Override
    public Long getBankId(String name) {
        log.info("Found bank id with name {}", name);
        return shopRepo.findByName(name).getId();
    }
}
