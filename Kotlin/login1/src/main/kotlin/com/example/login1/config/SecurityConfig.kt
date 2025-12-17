package com.example.login1.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService
) {

     @Bean
     fun passwordEncoder(): PasswordEncoder =
         BCryptPasswordEncoder(12)

//    @Bean
//    fun passwordEncoder() = NoOpPasswordEncoder.getInstance()

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder())
        return provider
    } 

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests {
                it.requestMatchers("/auth/register", "/auth/login")
                    .permitAll()
                it.anyRequest()
                    .authenticated()
            }
            .httpBasic { } // REST authentication
//            .sessionManagement {
//                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            }
            .build()
    }

    @Bean
    fun authManager (config : AuthenticationConfiguration) : AuthenticationManager {
        return config.authenticationManager;
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