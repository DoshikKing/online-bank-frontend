package com.banksource.onlinebank.payload.response.data;

import lombok.Data;

import java.sql.Date;

@Data
public class AccountData {

    private Long id;
    private String accountNumber;
    private Date statusTime;
    private float balance;
}
