package com.example.FinanceApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain sercurityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers(
                        "/h2-console/**",
                        "/error",
                        "/api/auth/**",
                        "/api/health"
                ).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form->form.disable())
                .httpBasic(basic->basic.disable())
                .headers(headers->headers.frameOptions(frame->frame.disable()));

        return http.build();
    }
}

