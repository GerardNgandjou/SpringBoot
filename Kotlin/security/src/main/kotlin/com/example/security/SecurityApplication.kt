package com.example.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@SpringBootApplication
class SecurityApplication

fun main(args: Array<String>) {
	runApplication<SecurityApplication>(*args)
}

@Configuration
class SecurityConfiguration {
	
	@Bean
	fun filterChain(http: HttpSecurity): SecurityFilterChain {
		http.authorizeHttpRequests { auth ->
			auth
				.requestMatchers("/admin.html").hasRole("ADMIN") // FIX
				.anyRequest().authenticated()
		}
		.formLogin { }     // enable form login
		// .httpBasic { }     // enable basic auth
		.logout{ }          // enable logout

		return http.build()
	}

	@Bean
	fun users(): UserDetailsService {
		val user = User.builder()
				.username("user")
				.password("{noop}myPassword123")
				.roles("USER")
				.build()
		val admin = User.builder()
				.username("admin")
				.password("{noop}myPassword123")
				.roles("USER", "ADMIN")
				.build()
		
		return InMemoryUserDetailsManager(user, admin)
	}

}
