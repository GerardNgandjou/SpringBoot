package com.example.testsecurity.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.http.ResponseEntity
import com.example.testsecurity.service.AuthService
import com.example.testsecurity.dto.LoginRequest
import jakarta.validation.Valid


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {
    
    // Login endpoint
    @PostMapping("/login")
    fun login(
        @Valid 
        @RequestBody 
        loginRequest: LoginRequest
    ): ResponseEntity<Map<String, String>> {
        // Authenticate user and generate JWT token
        val token = authService.login(loginRequest)
        
        // Return token in response
        return ResponseEntity.ok(mapOf("token" to token))
    }
    
    // Register endpoint (optional)
    @PostMapping("/register")
    fun register(
        @Valid 
        @RequestBody 
        loginRequest: LoginRequest
    ): ResponseEntity<Map<String, String>> {
        // Register new user
        authService.register(loginRequest.email, loginRequest.password)
        
        // Authenticate and return token
        val token = authService.login(loginRequest)
        return ResponseEntity.ok(mapOf("token" to token))
    }

    // Get All the user in the database
    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<Any> {
        val users = authService.getAllUsers()
        return ResponseEntity.ok(users)
    }

        @GetMapping("/public")
    fun publicEndpoint(): Map<String, String> {
        return mapOf("message" to "This is a public endpoint")
    }
    
    @GetMapping("/protected")
    fun protectedEndpoint(): Map<String, String> {
        return mapOf("message" to "This is a protected endpoint")
    }
}