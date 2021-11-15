package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface userServiceInterface {
    void addUser(User user);
    User findUser(String login);
    User findByClientId(Long id);
    UserDetails loadUserByUsername(String name);
    void updateById(String password, Boolean is_activated, Long id);
}
