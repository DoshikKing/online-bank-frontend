package com.banksource.onlinebank.payload.request.data;

import lombok.Data;

@Data
public class SignUpRequestData {

    private String login;
    private String password;
    private String repass;
    private String registration_code;
}
