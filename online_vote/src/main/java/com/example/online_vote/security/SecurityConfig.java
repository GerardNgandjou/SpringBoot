//package com.example.online_vote.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
////    @Autowired
////    private final LoginService loginService;
////
////    public SecurityConfig(LoginService loginService) {
////        this.loginService = loginService;
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Bean
////    public UserDetailsService userDetailsService() {
////        return loginService;
////    }
//
////    @Bean
////    public AuthenticationProvider authenticationProvider() {
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////        provider.setUserDetailsService(userDetailsService());
////        provider.setPasswordEncoder(passwordEncoder());
////        return provider;
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(httpForm -> {
//                    httpForm.loginPage("/voter/login").permitAll();
////                    httpForm.defaultSuccessUrl("/voter/register");
////                    httpForm.failureUrl("/login?error");
//                })
//                .authorizeHttpRequests(registry -> {
//                    registry.requestMatchers("/voter/register", "/css/**", "/js/**").permitAll();
//                    registry.anyRequest().authenticated();
//                })
//                .build();
//    }
//}
