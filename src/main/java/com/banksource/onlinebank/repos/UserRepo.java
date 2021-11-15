package com.banksource.onlinebank.repos;

import com.banksource.onlinebank.components.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String login);
    User findByClientId(Long id);
    @Modifying
    @Query("update User u set u.password = ?1, u.is_activated = ?2  where u.id = ?3")
    void setUserInfoById(String password,Boolean is_activated , Long id);
}
