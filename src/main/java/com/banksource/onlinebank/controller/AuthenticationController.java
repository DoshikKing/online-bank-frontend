package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.RefreshToken;
import com.banksource.onlinebank.components.User;
import com.banksource.onlinebank.exception.TokenRefreshException;
import com.banksource.onlinebank.payload.request.data.LogOutRequestData;
import com.banksource.onlinebank.payload.request.data.LoginRequestData;
import com.banksource.onlinebank.payload.request.data.SignUpRequestData;
import com.banksource.onlinebank.payload.request.data.TokenRefreshRequestData;
import com.banksource.onlinebank.payload.response.data.JwtResponseData;
import com.banksource.onlinebank.payload.response.data.RefreshTokenResponseData;
import com.banksource.onlinebank.payload.response.data.SimpleResponseData;
import com.banksource.onlinebank.security.jwt.JwtUtils;
import com.banksource.onlinebank.service.DTO.UserDTO;
import com.banksource.onlinebank.service.RefreshTokenService.RefreshTokenService;
import com.banksource.onlinebank.service.mainServices.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private com.banksource.onlinebank.service.mainServices.userService userService;

    private JwtUtils jwtUtils;

    private RefreshTokenService refreshTokenService;

    @Autowired
    public AuthenticationController (AuthenticationManager authenticationManager, JwtUtils jwtUtils, RefreshTokenService refreshTokenService,
                                     userService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        jwtResponseData.setAccessToken(jwt);
        jwtResponseData.setRefreshToken(refreshToken.getToken());
        jwtResponseData.setId(userDetails.getById());

        return ResponseEntity.ok(jwtResponseData);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> userRegistration(@Valid @RequestBody SignUpRequestData signUpRequestData){
        try {
            User user = userService.findUser(signUpRequestData.getLogin());
            SimpleResponseData simpleResponseData = new SimpleResponseData();

            if (!signUpRequestData.getLogin().equals(user.getLogin())){
                simpleResponseData.setComment("User with this username does not exist.");
                return new ResponseEntity<>(simpleResponseData, HttpStatus.NOT_FOUND);
            }

            if(!user.getClient().isActivated()){
                simpleResponseData.setComment("IB service is not activated! Contact with operational office for more information.");
                return new ResponseEntity<>(simpleResponseData, HttpStatus.BAD_REQUEST);
            }

            if(user.is_activated()){
                simpleResponseData.setComment("Your account was activated early. You can't activate your account again!");
                return new ResponseEntity<>(simpleResponseData, HttpStatus.BAD_REQUEST);
            }

            if(!signUpRequestData.getRegistration_code().equals(user.getRegistrationCode())){
                simpleResponseData.setComment("Control registration code is failed.");
                return new ResponseEntity<>(simpleResponseData, HttpStatus.BAD_REQUEST);
            }

            if (!signUpRequestData.getPassword().equals(signUpRequestData.getRepass())){
                simpleResponseData.setComment("Password mismatch.");
                return new ResponseEntity<>(simpleResponseData, HttpStatus.BAD_REQUEST);
            }

            try {
                userService.updateById(bCryptPasswordEncoder.encode(signUpRequestData.getPassword()), true, user.getId());
                simpleResponseData.setComment("Registered successfully.");
                return new ResponseEntity<>(simpleResponseData, HttpStatus.OK);
            } catch (UsernameNotFoundException e) {
                simpleResponseData.setComment("User with this username already exists");
                return new ResponseEntity<>(simpleResponseData, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

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
