package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.RefreshToken;
import com.banksource.onlinebank.components.User;
import com.banksource.onlinebank.exception.TokenRefreshException;
import com.banksource.onlinebank.payload.request.data.LogOutRequestData;
import com.banksource.onlinebank.payload.request.data.LoginRequestData;
import com.banksource.onlinebank.payload.request.data.TokenRefreshRequestData;
import com.banksource.onlinebank.payload.response.data.JwtResponseData;
import com.banksource.onlinebank.payload.response.data.RefreshTokenResponseData;
import com.banksource.onlinebank.payload.response.data.SimpleResponseData;
import com.banksource.onlinebank.security.jwt.JwtUtils;
import com.banksource.onlinebank.service.DTO.UserDTO;
import com.banksource.onlinebank.service.RefreshTokenService.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;

    private PasswordEncoder encoder;

    private JwtUtils jwtUtils;

    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticationController (AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestData loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDTO userDetails = (UserDTO) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getById());

        JwtResponseData jwtResponseData = new JwtResponseData();
        jwtResponseData.setToken(jwt);
        jwtResponseData.setRefreshToken(refreshToken.getToken());
        jwtResponseData.setId(userDetails.getById());

        return ResponseEntity.ok(jwtResponseData);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<?> refresh_token(@Valid @RequestBody TokenRefreshRequestData request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getLogin());
                    RefreshTokenResponseData refreshTokenResponseData = new RefreshTokenResponseData();
                    refreshTokenResponseData.setRefreshToken(requestRefreshToken);
                    refreshTokenResponseData.setAccessToken(token);
                    return ResponseEntity.ok(refreshTokenResponseData);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequestData logOutRequest) {
        refreshTokenService.deleteByUserId(logOutRequest.getUserId());
        SimpleResponseData simpleResponseData = new SimpleResponseData();
        simpleResponseData.setComment("Log out successful!");
        return ResponseEntity.ok(simpleResponseData);
    }

}
