package com.banksource.onlinebank.payload.response.data;

import lombok.Data;

@Data
public class ExecutableTransactionData {

    private Long debit_id;
    private Long credit_id;
    private float amount;
    private String comment;
}
