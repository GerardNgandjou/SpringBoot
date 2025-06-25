package com.example.vote.onlinevote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vote.onlinevote.model.User;

public interface UserRepository extends JpaRepository <User, Long>{

}
