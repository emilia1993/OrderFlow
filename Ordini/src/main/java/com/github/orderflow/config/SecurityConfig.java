package com.github.orderflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Swagger libero
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // GET ordini -> USER o ADMIN
                        .requestMatchers("/ordini").hasAnyRole("USER","ADMIN")

                        // POST PUT PATCH -> USER
                        .requestMatchers("/ordini/**").hasAnyRole("USER","ADMIN")

                        // DELETE -> ADMIN
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE,"/ordini/**")
                        .hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .httpBasic(httpBasic -> {});

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails user = User
                .builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User
                .builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}