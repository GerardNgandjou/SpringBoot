package com.example.login1.service

import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import com.example.login1.model.UserPrincipal
import com.example.login1.repository.UserRepository

@Service
class MyUserDetailsService(
    // Repository used to fetch users from the database
    private val userRepository: UserRepository
) : UserDetailsService {

    // This method is called by Spring Security during authentication
    override fun loadUserByUsername(username: String): UserDetails {

        // Find user by username from the database
        val user = userRepository.findByName(username)

        // If user does not exist, throw Spring Security exception
        if (user == null) {
            println("User not found: $username")
            throw UsernameNotFoundException("User not found")
        }

        // Convert your User entity into a UserDetails implementation
        return UserPrincipal(user)
    }
}
