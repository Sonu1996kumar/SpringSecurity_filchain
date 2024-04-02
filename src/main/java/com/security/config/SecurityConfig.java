package com.security.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {
    private Filter JWTRequestFilter;

    public SecurityConfig(Filter JWTRequestFilter) {
        this.JWTRequestFilter = JWTRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        //http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilterBefore(JWTRequestFilter, AuthorizationFilter.class);//taki hamara filter phle chale baki sab se
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/addUser","/api/v1/login")
                .permitAll()
                .requestMatchers("/api/v1/countries/addCountry").hasRole("ADMIN")
                .requestMatchers("/api/v1/profile").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated();
        return http.build();
    }
}
