package com.banksource.onlinebank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banksource.onlinebank.components.Record;
import java.util.List;
@Deprecated
public interface RecordRepo extends JpaRepository<Record, Long> {
    List<Record> findByOrderByDate();
    List<Record> findByOrderByTime();
    List<Record> findByOrderById();
    List<Record> findByOrderByShopId();
    Record findByDate(String cardNumber);
}
