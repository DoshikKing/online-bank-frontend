package com.banksource.onlinebank.security;

import com.banksource.onlinebank.components.Role;
import com.banksource.onlinebank.components.User;
import com.banksource.onlinebank.service.mainServices.RoleService;
import com.banksource.onlinebank.service.mainServices.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller @RequestMapping("/registration")
public class userController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private userService userService;
    private RoleService roleService;

    @Autowired
    public userController(userService userService, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String registration(){
        return "registration";
    }
    @PostMapping
    public String registration(@Valid SignUpRequest signUpRequest, BindingResult result, Model model){
        if (result.hasErrors()) return "registration";

        User user = userService.findUser(signUpRequest.getLogin());

        if (!signUpRequest.getLogin().equals(user.getLogin())){
            model.addAttribute("status","User with this username does not exist.");
            return "registration";
        }

        if(!user.getClient().isActivated()){
            model.addAttribute("status","IB service is not activated! Contact with operational office for more information.");
            return "registration";
        }

        if(user.is_activated()){
            model.addAttribute("status","Your account was activated early. You can't activate your account again!");
            return "registration";
        }

        if(!signUpRequest.getRegistration_code().equals(user.getRegistrationCode())){
            model.addAttribute("status","Control registration code is failed.");
            return "registration";
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getRepass())){
                model.addAttribute("status","Password mismatch.");
                return "registration";
        }

        try {
            userService.updateById(bCryptPasswordEncoder.encode(signUpRequest.getPassword()), true, user.getId());
            return "redirect:/login";
        } catch (UsernameNotFoundException e) {
            model.addAttribute("status","User with this username already exists");
            return "registration";
        }
    }
}
