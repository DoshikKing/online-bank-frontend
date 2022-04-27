package com.banksource.onlinebank.payload.response.data;

import lombok.Data;

import java.util.Date;


@Data
public class ErrorMessageData {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
