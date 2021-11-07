package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String login);
    User findByLoginAndPassword(String login, String password);
    User findByRegistration_code(String registration_code);
    User findByClientId(Long id);
}
