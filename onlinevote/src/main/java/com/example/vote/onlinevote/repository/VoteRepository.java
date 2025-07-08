package com.example.vote.onlinevote.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.vote.onlinevote.model.Candidate;
import com.example.vote.onlinevote.model.Election;
import com.example.vote.onlinevote.model.Vote;
import com.example.vote.onlinevote.model.Voter;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    boolean existsByVoterAndElection(Voter voter, Election election);
    
    List<Vote> findByVoter(Voter voter);
    List<Vote> findByElection(Election election);
    List<Vote> findByCandidate(Candidate candidate);
    
    // For statistics
    long countByElection(Election election);
    long countByCandidate(Candidate candidate);
}
