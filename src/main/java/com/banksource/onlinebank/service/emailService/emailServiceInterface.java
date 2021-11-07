package com.banksource.onlinebank.service.emailService;

import com.banksource.onlinebank.components.Shop;
import com.banksource.onlinebank.components.Record;
@Deprecated
public interface emailServiceInterface {

    void sendMessageAboutShop(Shop shop);

    void sendMessageAboutRecord(Record record);
}
