package org.example.recapspring.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${app.url}")
    private String appUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // NOSONAR: Disabling CSRF protection is safe here because [reason]
                .csrf(csrf -> csrf // CSRF-Konfiguration spezifizieren
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF-Token-Repository konfigurieren
                )
                .authorizeHttpRequests(a -> a
                        //Reihenfolge relevant! Erst die strengen Einschränkungen, weil es wird von oben nach unten durchgearbeitet
                        .requestMatchers(HttpMethod.DELETE, "/api/todo/*").authenticated()
                        .requestMatchers("/api/auth/me").authenticated()
                        .requestMatchers("/api/todo/*").permitAll()
                        .anyRequest().permitAll())

                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(o -> o.defaultSuccessUrl(appUrl))
                .logout(l -> l.logoutSuccessUrl(appUrl))
                .build();
    }

}