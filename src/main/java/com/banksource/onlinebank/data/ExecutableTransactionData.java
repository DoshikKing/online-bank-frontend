package com.banksource.onlinebank.data;

import lombok.Data;

@Data
public class ExecutableTransactionData {

    private String debit_id_hash_code;
    private String credit_id_hash_code;
    private float amount;
    private String comment;
}
