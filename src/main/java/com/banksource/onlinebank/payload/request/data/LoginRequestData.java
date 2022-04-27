package com.banksource.onlinebank.payload.request.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestData {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
