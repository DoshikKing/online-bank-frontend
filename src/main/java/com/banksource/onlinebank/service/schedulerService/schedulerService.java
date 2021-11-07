package com.banksource.onlinebank.service.schedulerService;

import com.banksource.onlinebank.components.Shop;
import com.banksource.onlinebank.components.Record;
import com.banksource.onlinebank.service.mainServices.RecordService;
import com.banksource.onlinebank.service.mainServices.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@Component
@RequiredArgsConstructor
@ManagedResource(objectName = "MyMBeans:category=MBeans, name=schedulerService")
public class schedulerService implements schedulerInterface {

    @Autowired
    ShopService shopService;
    @Autowired
    RecordService recordService;

    @Async
    @Scheduled(fixedDelay = 1800000)
    @ManagedOperation(description = "Deletes all content of the directory and writes all data from the databases to it every 30 minutes")
    @Override
    public void createSQLCopy() {
        String path = "src/main/alldb";

        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File file: files) file.delete();

        try {
            File banks = new File(path + "/shops.txt");
            banks.createNewFile();
            try(FileWriter writer = new FileWriter(banks, true);){
                List<Shop> shopList = shopService.getAllShops();
                for (Shop item : shopList) {
                    writer.write("id "+item.getId()+ " name " + item.getName() + " address" + item.getAddress() + "\n");
                }
                writer.flush();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }

            File cards = new File(path + "/records.txt");
            cards.createNewFile();
            try(FileWriter writer = new FileWriter(cards, true);){
                List<Record> recordList = recordService.getAllRecords();
                for (Record item : recordList) {
                    writer.write("id "+item.getId()+ " date " + item.getDate() + " time " + item.getTime() + " shops id " + item.getShop().getId() + "\n");
                }
                writer.flush();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
