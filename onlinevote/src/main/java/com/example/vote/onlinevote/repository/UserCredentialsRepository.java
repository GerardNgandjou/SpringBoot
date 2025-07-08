package com.example.vote.onlinevote.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vote.onlinevote.model.UserCredentials;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> findByEmail(String email);

    boolean existsByEmail(String email);
}
