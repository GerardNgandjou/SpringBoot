package com.example.login1.service

import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import com.example.login1.model.UserPrincipal
import com.example.login1.repository.UserRepository

@Service
class MyUserDetailsService (
    private val userRepository : UserRepository
) : UserDetailsService {
 
    override fun loadUserByUsername(username: String): UserDetails { 

        val users = userRepository.findByName(username)

        if(users == null) {
            println("User not found")
            throw UsernameNotFoundException("User not found")
        }

        return UserPrincipal(users);
    }
}