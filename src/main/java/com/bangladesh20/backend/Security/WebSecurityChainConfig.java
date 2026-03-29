package com.bangladesh20.backend.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityChainConfig {
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf().disable()

                // 2. Set Session to Stateless (Standard for JWT/REST)
                .sessionManagement()

                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()

                // 3. Define Permissions
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // Match any auth sub-paths
//                .antMatchers("/admin/**").permitAll() // Match any auth sub-paths
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("https://bangladesh-20-production.up.railway.app"));
        configuration.setAllowedOrigins(Arrays.asList("https://bangladesh-20-backend-production-07c7.up.railway.app"));
//        configuration.setAllowedOrigins(Arrays.asList("https://shanto-bangladesh-2-0.netlify.app"));
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); //For Local
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setExposedHeaders(Arrays.asList("Set-Cookie"));
//        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
