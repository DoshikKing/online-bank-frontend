package com.banksource.onlinebank.work;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;



@Component
public class Headers {

    public HttpHeaders getHeaders(){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "http://localhost:3000");
        responseHeaders.set("Access-Control-Allow-Headers","origin, content-type, accept, authorization");
        return responseHeaders;
    }
}
