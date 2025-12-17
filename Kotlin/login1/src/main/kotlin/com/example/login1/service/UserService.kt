package com.example.login1.service

import com.example.login1.model.Users
import com.example.login1.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService (
    private val repo: UserRepository,
    private val authManager: AuthenticationManager,
    private val jwtService: JWTService
) {

    // fun passwordEncoder(): PasswordEncoder {
    //     return BCryptPasswordEncoder(12)
    // } 
    

    // fun register(user: Users): Users {
    //     // user.password = passwordEncoder().encode(user.password)
    //     user.password = passwordEncoder().encode(user.password)
    //     return repo.save(user)
    // }

    private val encoder = BCryptPasswordEncoder(12)

    fun register(user: Users): Users {
        val encodedPassword = encoder.encode(user.password) ?: 
            throw IllegalStateException("Password encoding failed")
        
        val userWithEncodedPassword = user.copy(password = encodedPassword)
        return repo.save(userWithEncodedPassword)
    }

    fun verify(users: Users): String {
        val authenticationManager: Authentication =
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(users.name, users.password)
            )
        if (authenticationManager.isAuthenticated())
            return jwtService.generateToken(users.name)

        return "fail"
    }

}