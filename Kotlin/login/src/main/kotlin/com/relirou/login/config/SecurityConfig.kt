package com.relirou.login.config

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Autowired
import com.relirou.login.service.MyUserDetailsService

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val userDetailsService: UserDetailsService
) {

    @Bean
    fun passwordEncoder() = NoOpPasswordEncoder.getInstance()

    @Bean
    fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain {

        return http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                // it.anyRequest().authenticated()
                it.anyRequest().permitAll()
            }
            .formLogin{}
            .httpBasic{}
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .build()

    } 

    @Bean
    fun authenticationProvider(): AuthenticationProvider {

        val provider = DaoAuthenticationProvider(userDetailsService)
        provider.setPasswordEncoder(passwordEncoder())
        // provider.setUserDetailsService(userDetailsService)

        return provider
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