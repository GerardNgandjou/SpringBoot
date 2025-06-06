package com.example.vote.onlinevote.repository;

import com.example.vote.onlinevote.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findAllByFirstnameContaining(String fn);
}
