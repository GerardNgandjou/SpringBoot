package com.example.vote.onlinevote.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email                
        String email,
        @Size(min = 8)        
        String password
) {}