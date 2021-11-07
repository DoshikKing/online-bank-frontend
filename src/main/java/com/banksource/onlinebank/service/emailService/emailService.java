package com.banksource.onlinebank.service.emailService;

import com.banksource.onlinebank.components.Shop;
import com.banksource.onlinebank.components.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Deprecated
public class emailService implements emailServiceInterface{

    public JavaMailSender javaMailSender;

    @Autowired
    public emailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendMessageAboutShop(Shop shop){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("qwertyuiop12345jomh@gmail.com");
        simpleMailMessage.setSubject("Added bank");
        simpleMailMessage.setText("Added shop: name - " +
                shop.getName() + " address - " + shop.getAddress());
        javaMailSender.send(simpleMailMessage);
    }
    @Async
    public void sendMessageAboutRecord(Record record){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("qwertyuiop12345jomh@gmail.com");
        simpleMailMessage.setSubject("Added record");
        simpleMailMessage.setText("Added Record: date - " +
                record.getDate() + " time - " + record.getTime());
        javaMailSender.send(simpleMailMessage);
    }
}
