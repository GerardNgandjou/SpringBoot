package com.example.online_vote.voter;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<VoterLogin, Integer> {
}
