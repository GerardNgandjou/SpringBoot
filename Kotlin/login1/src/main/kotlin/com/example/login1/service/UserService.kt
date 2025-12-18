package com.example.login1.service

import com.example.login1.model.Users
import com.example.login1.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    // Repository for database operations
    private val repo: UserRepository,

    // Spring Security authentication manager
    private val authManager: AuthenticationManager,

    // Service responsible for JWT generation
    private val jwtService: JWTService
) {

    // Password encoder (BCrypt with strength 12)
    private val encoder = BCryptPasswordEncoder(12)

    // Register a new user
    fun register(user: Users): Users {

        // Encode the raw password before saving
        val encodedPassword = encoder.encode(user.password)
            ?: throw IllegalStateException("Password encoding failed")

        // Create a new user object with the encoded password
        val userWithEncodedPassword = user.copy(
            password = encodedPassword
        )

        // Save the user to the database
        return repo.save(userWithEncodedPassword)
    }

    // Authenticate user credentials and return JWT if successful
    fun verify(users: Users): String {

        // Authenticate username and password
        val authentication: Authentication =
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    users.name,
                    users.password
                )
            )

        // If authentication is successful, generate JWT
        if (authentication.isAuthenticated) {
            return jwtService.generateToken(users.name)
        }

        // Authentication failed
        return "fail"
    }
}
