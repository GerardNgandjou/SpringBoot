package com.example.vote.onlinevote.dto;

import java.util.Set;

import com.example.vote.onlinevote.model.UserCredentials.Role;

public record AuthResponse(
        Long   id,
        String email,
        Set<Role> roles
) {}