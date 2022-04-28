package com.banksource.onlinebank.payload.response.data;

import lombok.Data;

@Data
public class JwtResponseData {
    private String accessToken;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
//    private String username;
//    private String email;
//    private List<String> roles;
}
