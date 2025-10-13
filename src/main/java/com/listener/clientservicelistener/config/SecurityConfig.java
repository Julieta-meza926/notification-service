package com.listener.clientservicelistener.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())   // desactiva CSRF para Postman
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // permite todas las requests
                )
                .formLogin(login -> login.disable())   // desactiva login form
                .httpBasic(basic -> basic.disable());  // desactiva basic auth

        return http.build();
    }
}
