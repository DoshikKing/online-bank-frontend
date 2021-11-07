package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Deprecated
public interface ShopRepo extends JpaRepository<Shop, Long> {
    List<Shop> findByOrderByName();
    List<Shop> findByOrderByAddress();
    List<Shop> findByOrderById();
    Shop findByName(String name);
}
