package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface userServiceInterface {
    Optional<User> findById(Long id);
    void addUser(User user);
    User findUser(String login);
    User findByClientId(Long id);
    UserDetails loadUserByUsername(String name);
    void updateById(String password, Boolean is_activated, Long id);
}
