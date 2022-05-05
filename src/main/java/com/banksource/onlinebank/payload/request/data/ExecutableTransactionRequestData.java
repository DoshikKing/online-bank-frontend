package com.banksource.onlinebank.payload.request.data;

import lombok.Data;

@Data
public class ExecutableTransactionRequestData {

    private Long debit_id;
    private Long credit_id;
    private float amount;
    private String comment;
}
