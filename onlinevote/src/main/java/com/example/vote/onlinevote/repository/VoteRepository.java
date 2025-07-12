package com.example.vote.onlinevote.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vote.onlinevote.model.Election;
import com.example.vote.onlinevote.model.Vote;
import com.example.vote.onlinevote.model.Voter;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    boolean existsByVoterAndElection(Voter voter, Election election);
}
