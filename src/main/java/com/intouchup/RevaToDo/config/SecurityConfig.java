package com.intouchup.RevaToDo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.security.autoconfigure.web.servlet.PathRequest.toH2Console;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())//enforces CorsFilter bean config
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(toH2Console()).permitAll() // Allow all access to the H2 console path
                        .requestMatchers("/auth/**").permitAll()// Allow all access to auth path
                        .anyRequest().authenticated() // Secure other requests
                )
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers(toH2Console()) // Disable CSRF for H2 console
//                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions
                                .sameOrigin() // Allow frames from the same origin (required for H2 console UI)
                        )
                );
        return http.build();
    }
}
