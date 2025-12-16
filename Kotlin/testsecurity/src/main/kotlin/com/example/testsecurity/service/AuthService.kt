package com.example.testsecurity.service

import org.springframework.stereotype.Service
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.core.Authentication
import com.example.testsecurity.repository.UserRepository
import com.example.testsecurity.dto.LoginRequest
import com.example.testsecurity.model.User
import java.time.LocalDateTime

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder
) {
    
    // Authenticate user and generate JWT token
    fun login(loginRequest: LoginRequest): String {
        // Authenticate user with Spring Security
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.password
            )
        )
        
        // Generate JWT token if authentication is successful
        // Use elvis operator to provide default value if name is null
        val username = authentication.name ?: loginRequest.email
        return jwtService.generateToken(username)
    }
    
    // Register a new user (optional - for completeness)
    fun register(email: String, password: String): User {
        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            throw IllegalArgumentException("User with email $email already exists")
        }

        val encodedPassword = passwordEncoder.encode(password) ?:
            throw IllegalStateException("Failed to encoding password")
        
        // Create new user with encoded password
        val user = User(
            email = email,
            passwordHash = encodedPassword,  // Encode password
            updatedAt = LocalDateTime.now()
        )
        
        // Save user to database
        return userRepository.save(user)
    }

    // Get all the users in the database
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    
}