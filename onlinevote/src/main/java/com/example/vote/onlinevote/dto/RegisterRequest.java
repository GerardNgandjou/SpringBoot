package com.example.vote.onlinevote.dto;

import java.util.Set;

import com.example.vote.onlinevote.model.UserCredentials.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Email                
        String email,
        @Size(min = 8)        
        String password,
        Set<Role> roles
) {}