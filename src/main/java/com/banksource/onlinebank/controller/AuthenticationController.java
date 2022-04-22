package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.User;
import com.banksource.onlinebank.service.mainServices.userService;
import com.banksource.onlinebank.work.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/login", produces="application/json")
public class AuthenticationController {

    private com.banksource.onlinebank.service.mainServices.userService userService;
    private Headers headers;

    @Autowired
    public AuthenticationController(userService userService, Headers headers){
        this.userService = userService;
        this.headers = headers;
    }

    @GetMapping
    public ResponseEntity login(Authentication authentication){
        try{
            User user =  userService.findUser(authentication.getName());
            if(user != null){
                return ResponseEntity.ok().headers(headers.getHeaders()).body(HttpStatus.OK);
            }
            return ResponseEntity.notFound().headers(headers.getHeaders()).build();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.internalServerError().headers(headers.getHeaders()).body(exception.getMessage());
        }
    }
}
