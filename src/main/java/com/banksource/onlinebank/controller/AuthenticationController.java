package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.User;
import com.banksource.onlinebank.service.mainServices.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/login", produces="application/json")
public class AuthenticationController {

    private com.banksource.onlinebank.service.mainServices.userService userService;

    @Autowired
    public AuthenticationController(userService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity login(Authentication authentication){
        try{
            User user =  userService.findUser(authentication.getName());
            if(user != null){
                return ResponseEntity.ok(HttpStatus.OK);
            }
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
