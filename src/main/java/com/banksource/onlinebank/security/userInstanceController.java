package com.banksource.onlinebank.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Deprecated
@Controller
public class userInstanceController {

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }
}
