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
            .csrf().disable() // Desativa a proteção CSRF (temporariamente)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/usuarios").permitAll() // Permite acesso sem autenticação
                .anyRequest().authenticated()
            );
        return http.build();
    }
}
