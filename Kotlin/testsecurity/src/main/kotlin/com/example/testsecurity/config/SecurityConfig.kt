package com.example.testsecurity.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.authentication.AuthenticationManager
import com.example.testsecurity.filter.JwtAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }  // Disable CSRF for JWT (stateless)
            .cors{
                cors -> cors.configure(http)
            }
            .sessionManagement { session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless sessions
            }
            .authorizeHttpRequests { auth ->
                auth
                    // ✅ Allow ALL auth endpoints without authentication
                    .requestMatchers("/api/auth/**").permitAll()
                    // ✅ Allow error endpoint
                    .requestMatchers("/error").permitAll()
                    // ✅ Allow Swagger/OpenAPI if using
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/webjars/**"
                    ).permitAll()
                    // ✅ All other endpoints require authentication
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        
        return http.build()
    }
    
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()  // BCrypt password encoder
    }
    
    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }
}