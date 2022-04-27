package com.banksource.onlinebank.payload.response.data;

import lombok.Data;

@Data
public class RefreshTokenResponseData {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
