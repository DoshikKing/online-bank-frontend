package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Shop;

import java.util.List;
@Deprecated
public interface shopServiceInterface {
    void addShop(Shop shop);
    List<Shop> getAllShops();
    List<Shop> filterByName();
    List<Shop> filterByAddress();
    List<Shop> filterByShopId();
    Shop getShopById(Long id);
    void deleteShopById(Long id);
    void deleteAllShops();
    Long getBankId(String name);
}
