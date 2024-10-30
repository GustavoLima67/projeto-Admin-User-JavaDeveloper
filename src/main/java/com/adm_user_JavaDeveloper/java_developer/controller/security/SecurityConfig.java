package com.adm_user_JavaDeveloper.java_developer.controller.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll() // permite acesso a todas as rotas: GET, POST, PUT, DEL.
        );

    return http.build();
    }
}
