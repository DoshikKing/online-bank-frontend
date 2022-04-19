package com.banksource.onlinebank.data;

import lombok.Data;

import java.sql.Date;

@Data
public class TransactionData {

    private String comment;
    private float summ;
    private Boolean isDebit;
    private Date transactionTime;
    private String code;
}
