package com.microcommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/clients/**").permitAll() // autoriser H2 Console et API clients
                        .anyRequest().permitAll() // autoriser tout pour le développement
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/clients/**") // désactiver CSRF pour H2 et API
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin()) // permettre iframe pour H2
                )
                .formLogin(form -> form.disable()); // désactiver formulaire login

        return http.build();
    }
}
