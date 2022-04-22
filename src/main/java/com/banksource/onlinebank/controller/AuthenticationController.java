package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.User;
import com.banksource.onlinebank.service.mainServices.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
                return ResponseEntity.ok().body("{data: " + HttpStatus.OK + "}");
            }
            return ResponseEntity.notFound().build();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
