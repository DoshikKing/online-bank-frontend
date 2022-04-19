package com.banksource.onlinebank.security;

import lombok.Data;

@Data
public class SignUpRequest {

    private String login;
    private String password;
    private String repass;
    private String registration_code;
}
