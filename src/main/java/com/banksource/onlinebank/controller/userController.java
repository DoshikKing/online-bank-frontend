package com.banksource.onlinebank.controller;

import com.banksource.onlinebank.components.User;
import com.banksource.onlinebank.payload.request.data.SignUpRequestData;
import com.banksource.onlinebank.payload.response.data.SimpleResponseData;
import com.banksource.onlinebank.service.mainServices.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@Controller @RequestMapping("/registration")
//public class userController {
//
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    private userService userService;
//    private RoleService roleService;
//
//    @Autowired
//    public userController(userService userService, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService){
//        this.userService = userService;
//        this.roleService = roleService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    @GetMapping
//    public String registration(){
//        return "registration";
//    }
//    @PostMapping
//    public String registration(@Valid SignUpRequestData signUpRequest, BindingResult result, Model model){
//        if (result.hasErrors()) return "registration";
//
//        User user = userService.findUser(signUpRequest.getLogin());
//
//        if (!signUpRequest.getLogin().equals(user.getLogin())){
//            model.addAttribute("status","User with this username does not exist.");
//            return "registration";
//        }
//
//        if(!user.getClient().isActivated()){
//            model.addAttribute("status","IB service is not activated! Contact with operational office for more information.");
//            return "registration";
//        }
//
//        if(user.is_activated()){
//            model.addAttribute("status","Your account was activated early. You can't activate your account again!");
//            return "registration";
//        }
//
//        if(!signUpRequest.getRegistration_code().equals(user.getRegistrationCode())){
//            model.addAttribute("status","Control registration code is failed.");
//            return "registration";
//        }
//
//        if (!signUpRequest.getPassword().equals(signUpRequest.getRepass())){
//                model.addAttribute("status","Password mismatch.");
//                return "registration";
//        }
//
//        try {
//            userService.updateById(bCryptPasswordEncoder.encode(signUpRequest.getPassword()), true, user.getId());
//            return "redirect:/login";
//        } catch (UsernameNotFoundException e) {
//            model.addAttribute("status","User with this username already exists");
//            return "registration";
//        }
//    }
//}

@RestController
@RequestMapping("/api/registration")
public class userController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private userService userService;

    @Autowired
    public userController(userService userService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String registration(){
        return "registration";
    }

    @PostMapping
    public ResponseEntity<SimpleResponseData> registration(@Valid @RequestBody SignUpRequestData signUpRequestData){
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
}
