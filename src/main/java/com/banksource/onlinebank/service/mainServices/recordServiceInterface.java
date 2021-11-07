package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Shop;
import com.banksource.onlinebank.components.Record;

import java.util.List;
@Deprecated
public interface recordServiceInterface {
    void addRecord(Record record);
    List<Record> getAllRecords();
    List<Record> filterByDate();
    List<Record> filterByTime();
    List<Record> filterByRecordId();
    List<Record> filterByRelatedShopId();
    Record getRecordById(Long id);
    void deleteRecordById(Long id);
    void deleteAllRecords();
    Shop getShopByRecord(Long id);
    Long getRecordId(String date);
}
