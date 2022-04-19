package com.banksource.onlinebank.work;

import org.springframework.stereotype.Component;

@Component
public class CheckAuthentication {

    public boolean check(String login, String auth_login){
        return login.equals(auth_login);
    }
}
