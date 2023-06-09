package com.abc.farms.abcfarmsbackendjava.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final String[] AUTH_WHITELIST = {
            "/error",
            "/api/ping",
            // "/api/users/**",
            "/api/users/register",
            "/api/users/login",
            "/api/users/verify-email",
            "/api/users/resend-verification-email",
            "/api/users/reset-password",
    };

    // private final String[] ADMIN_ROUTES = {""};

    private final JwtAuthenticationFilter jwtRequestFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        // .requestMatchers(ADMIN_ROUTES)
                        // .hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf()
                .disable()
                .cors()
                .configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();
                        configuration.setAllowedOrigins(Arrays.asList("*"));
                        configuration.setAllowedMethods(Arrays.asList("*"));
                        configuration.setAllowCredentials(false);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setExposedHeaders(Arrays.asList("Authorization", "content-type"));

                        return configuration;
                    }
                });

        return http.build();
        // http
        // .cors()
        // .configurationSource(corsConfigurationSource())
        // .and()
        // .csrf()
        // .disable()
        // .authorizeRequests()
        // .requestMatchers(AUTH_WHITELIST)
        // // .requestMatchers(AUTH_WHITELIST)
        // .permitAll()
        // // .requestMatchers(ADMIN_ROUTES)
        // // .hasRole("ADMIN")
        // .anyRequest()
        // .authenticated()
        // .and()
        // .sessionManagement()
        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // .and()
        // .authenticationProvider(authenticationProvider)
        // .addFilterBefore(jwtRequestFilter,
        // UsernamePasswordAuthenticationFilter.class);

        // return http.build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        // configuration.setAllowedOrigins(Arrays.asList("https://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        // configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
        // "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
