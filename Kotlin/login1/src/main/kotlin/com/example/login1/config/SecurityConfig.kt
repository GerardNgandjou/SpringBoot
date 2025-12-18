package com.example.login1.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
@Configuration
@EnableWebSecurity
class SecurityConfig(
    // Custom UserDetailsService used for authentication
    private val userDetailsService: UserDetailsService,

    // JWT filter executed before username/password authentication
    private val jwtFilter: JwtFilter
) {

    // Password encoder used to hash and verify passwords
    @Bean
    fun passwordEncoder(): PasswordEncoder =
        BCryptPasswordEncoder(12)

    // ⚠️ Only for testing — DO NOT use in production
//    @Bean
//    fun passwordEncoder() = NoOpPasswordEncoder.getInstance()

    // Authentication provider that uses UserDetailsService + PasswordEncoder
    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder())
        return provider
    }

    // Main Spring Security filter chain configuration
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            // Disable CSRF because we use JWT (stateless authentication)
            .csrf { it.disable() }

            // Register the custom authentication provider
            .authenticationProvider(authenticationProvider())

            // Authorization rules
            .authorizeHttpRequests {
                // Public endpoints (no authentication required)
                it.requestMatchers("/auth/register", "/auth/login")
                    .permitAll()

                // All other endpoints require authentication
                it.anyRequest()
                    .authenticated()
            }

            // Enable HTTP Basic (useful for testing / tools like Postman)
            .httpBasic { }

            // Make the application stateless (no HTTP session)
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

            // Add JWT filter before Spring Security's authentication filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

            // Build the security configuration
            .build()
    }

    // Expose AuthenticationManager as a Spring Bean
    @Bean
    fun authManager(
        config: AuthenticationConfiguration
    ): AuthenticationManager {
        return config.authenticationManager
    }


    // @Bean
    // fun userDetailsService(): UserDetailsService {

    //     val user1 : UserDetails = User
    //                     .withDefaultPasswordEncoder()
    //                     .name("relirou")
    //                     .password("r@123")
    //                     .roles("USER")
    //                     .build()

    //     val user2 : UserDetails = User
    //                     .withDefaultPasswordEncoder()
    //                     .name("admin")
    //                     .password("admin@123")
    //                     .roles("ADMIN")
    //                     .build()

    //     return InMemoryUserDetailsManager(user1, user2)

    // }

}