package com.banksource.onlinebank.security;

import com.banksource.onlinebank.service.mainServices.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private userService userService;

    @Override
    protected void configure(HttpSecurity http) throws
            Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/welcome", "/login", "/registration").permitAll()
                .antMatchers("/errorPage",
                        "/logout", "/home", "/home/addRecord", "/home/removeRecord").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/shopErrorPage", "/home/addShop",
                        "/home/removeShop", "/home/getRecordShop",
                        "/home/getShopByName", "/home/getShopById",
                        "/home/getShopByAddress", "/home/getRecordById",
                        "/home/getRecordByTime", "/home/getRecordByDate", "/admin").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().defaultSuccessUrl("/forward").permitAll()
                .and().logout().
                    invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/welcome")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception{
        managerBuilder.authenticationProvider(authenticationProvider());
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return  new userService();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider =  new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(encoder());

        return authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

