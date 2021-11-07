package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.Shop;
import com.banksource.onlinebank.components.Record;
import com.banksource.onlinebank.repos.RecordRepo;
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
public class RecordService implements recordServiceInterface {
    RecordRepo recordRepo;
    emailService emailService;

    @Autowired
    public RecordService(RecordRepo recordRepo, emailService emailService){
        this.emailService = emailService;
        this.recordRepo = recordRepo;
    }

    public RecordService(RecordRepo recordRepo) {
        this.recordRepo = recordRepo;
    }

    @Override
    public void addRecord(Record record) {
        log.info("Added new record {}", record);
        //email service
        //emailService.sendMessageAboutCard(card);
        recordRepo.save(record);
    }

    @Override
    public List<Record> getAllRecords() {
        log.info("Found all records");
        List<Record> recordList = recordRepo.findAll();
        return recordList;
    }

    @Override
    public List<Record> filterByDate() {
        log.info("Sorted record list by date");
        return recordRepo.findByOrderByDate();
    }

    @Override
    public List<Record> filterByTime() {
        log.info("Sorted record list by time");
        return recordRepo.findByOrderByTime();
    }

    @Override
    public List<Record> filterByRecordId() {
        log.info("Sorted record list by id");
        return recordRepo.findByOrderById();
    }

    @Override
    public List<Record> filterByRelatedShopId() {
        log.info("Sorted record list by related shops id");
        return recordRepo.findByOrderByShopId();
    }

    @Override
    public Record getRecordById(Long id) {
        log.info("Found a record with id {}", id);
        return recordRepo.getById(id);
    }

    @Override
    public void deleteRecordById(Long id) {
        log.info("Deleted record wit id {}", id);
        recordRepo.deleteById(id);
    }

    @Override
    public void deleteAllRecords() {
        log.info("All records deleted");
        recordRepo.deleteAll();
    }

    @Override
    public Shop getShopByRecord(Long id) {
        log.info("Got bank by record");
        return recordRepo.findById(id).orElseThrow(() ->
                new IllegalStateException("record with this id not found")).getShop();
    }

    @Override
    public Long getRecordId(String date) {
        log.info("Found id of the record with the date {}", date);
        return recordRepo.findByDate(date).getId();
    }
}
