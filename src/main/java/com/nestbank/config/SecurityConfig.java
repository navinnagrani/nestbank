package com.nestbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                //Disable CSRF completely for REST APIs
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {})

                //Required for H2 Console
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )

                //Stateless JWT-based security
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                //Disable default auth mechanisms
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                //Authorization rules
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/auth/**", "/h2-console/**", "/error").permitAll()

                        // ADMIN ONLY
                        .requestMatchers("/customers/**").hasRole("ADMIN")
                        .requestMatchers("/accounts/create").hasRole("ADMIN")

                        // CUSTOMER ONLY
                        .requestMatchers("/payments/transfer").hasRole("CUSTOMER")
                        .requestMatchers("/transactions/**").hasRole("CUSTOMER")

                        // BOTH CAN VIEW ACCOUNTS
                        .requestMatchers("/accounts/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers("/accounts/customer/**").hasRole("CUSTOMER")

                        .anyRequest().authenticated()
                )

                //JWT filter (runs after auth endpoints)
                .addFilterBefore(
                        new JwtFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
