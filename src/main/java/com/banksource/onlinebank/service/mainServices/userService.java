package com.banksource.onlinebank.service.mainServices;

import com.banksource.onlinebank.components.User;
import com.banksource.onlinebank.repos.UserRepo;
import com.banksource.onlinebank.service.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Component
@Transactional
public class userService implements userServiceInterface, UserDetailsService {
    @Autowired
    UserRepo userRepo;

    public userService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public userService() {

    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public void addUser(User user) {
        if(findUser(user.getLogin()) != null){
            throw new UsernameNotFoundException("Exist");
        }
        userRepo.save(user);
    }

    @Override
    public com.banksource.onlinebank.components.User findUser(String login) {
        return userRepo.findByLogin(login);
    }

    @Override
    public User findByClientId(Long id) {
        return userRepo.findByClientId(id);
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        User user = findUser(name);
        if (user == null)
        {
            throw new UsernameNotFoundException("Not found");
        }
        return new UserDTO(user);
    }

    @Override
    public void updateById(String password, Boolean is_activated, Long id) {
        userRepo.setUserInfoById(password, is_activated, id);
    }
}
