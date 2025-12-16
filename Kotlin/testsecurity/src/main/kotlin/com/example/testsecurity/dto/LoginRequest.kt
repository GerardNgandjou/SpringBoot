package com.example.testsecurity.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Email


// Data Transfer Object for login requests
data class LoginRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Email should be valid")
    val email: String,
    
    @field:NotBlank(message = "Password is required")
    val password: String
)