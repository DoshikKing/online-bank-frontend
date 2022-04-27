package com.banksource.onlinebank.payload.request.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRefreshRequestData {
    @NotBlank
    private String refreshToken;
}
