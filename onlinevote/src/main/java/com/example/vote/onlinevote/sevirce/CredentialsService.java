package com.example.vote.onlinevote.sevirce;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.vote.onlinevote.dto.AuthResponse;
import com.example.vote.onlinevote.dto.LoginRequest;
import com.example.vote.onlinevote.dto.RegisterRequest;
import com.example.vote.onlinevote.model.UserCredentials;
import com.example.vote.onlinevote.repository.UserCredentialsRepository;

import jakarta.transaction.Transactional;

@Service
public class CredentialsService {

    private final UserCredentialsRepository repo;
    private final PasswordEncoder encoder;

    public CredentialsService(UserCredentialsRepository repo,
                              PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        if (repo.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        UserCredentials saved = repo.save(
                UserCredentials.builder()
                        .email(req.email())
                        .passwordHash(encoder.encode(req.password()))
                        .roles(req.roles().isEmpty()
                               ? Set.of(UserCredentials.Role.VOTER)
                               : req.roles())
                        .build());

        return new AuthResponse(saved.getId(), saved.getEmail(), saved.getRoles());
    }

    public AuthResponse authenticate(LoginRequest req) {
        UserCredentials user = repo.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("Bad credentials"));

        if (!encoder.matches(req.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Bad credentials");
        }
        return new AuthResponse(user.getId(), user.getEmail(), user.getRoles());
    }
}
