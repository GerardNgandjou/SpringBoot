package com.example.vote.onlinevote.repository;

import com.example.vote.onlinevote.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, Long> {
//    Voter findByUsername(String username);
}
