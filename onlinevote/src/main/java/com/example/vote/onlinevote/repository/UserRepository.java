package com.example.vote.onlinevote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vote.onlinevote.model.User;

public interface UserRepository extends JpaRepository <User, Long>{

    List<User> findByRoleNot(String string);

}
