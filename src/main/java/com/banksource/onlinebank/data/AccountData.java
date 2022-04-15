package com.banksource.onlinebank.data;

import lombok.Data;

import java.sql.Date;

@Data
public class AccountData {

    private String accountNumber;
    private Date statusTime;
    private float balance;
}
