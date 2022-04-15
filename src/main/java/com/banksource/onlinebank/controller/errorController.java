package com.banksource.onlinebank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/error")
public class errorController {
    @GetMapping
    public ResponseEntity errorGet() {
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }
    @PostMapping
    public ResponseEntity errorPost() {
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }
}
