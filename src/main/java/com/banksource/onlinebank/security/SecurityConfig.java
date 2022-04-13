package com.banksource.onlinebank.security;

import com.banksource.onlinebank.service.mainServices.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// TODO: Подумать об использовании JWT токена вместо Basic

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private userService userService;
    private AuthenticationEntryPoint authEntryPoint;

    // TODO: Разобраться с старым конфигом
//    @Override
//    protected void configure(HttpSecurity http) throws
//            Exception {
//        http
//                .csrf()
//                .disable()
//                .cors()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/welcome", "/login", "/registration").permitAll()
//                .antMatchers("/errorPage",
//                        "/logout", "/home", "/home/addRecord", "/home/removeRecord").hasAnyAuthority("USER", "ADMIN")
//                .antMatchers("/shopErrorPage", "/home/addShop",
//                        "/home/removeShop", "/home/getRecordShop",
//                        "/home/getShopByName", "/home/getShopById",
//                        "/home/getShopByAddress", "/home/getRecordById",
//                        "/home/getRecordByTime", "/home/getRecordByDate", "/admin").hasAuthority("ADMIN")
//                .anyRequest().authenticated()
//                .and().formLogin().defaultSuccessUrl("/success").permitAll()
//                .and().logout().
//                    invalidateHttpSession(true)
//                    .clearAuthentication(true)
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .logoutSuccessUrl("/welcome")
//                .permitAll();
//    }
    // TODO: Нужно добавить фильтрацию по ролям пользователей
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/test/data").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic()//.authenticationEntryPoint(authEntryPoint)
                //.and().formLogin().defaultSuccessUrl("/success").permitAll()
                .and().logout().
                invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/welcome")
                .permitAll();
    // TODO: Фильтр доделать

    //        http.addFilterAfter(new CustomFilter(),
    //                BasicAuthenticationFilter.class);
    }

    // TODO: Разобраться с ниже перечисленными функциями. Нужны или нет.
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

    // TODO: Это нужно
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

