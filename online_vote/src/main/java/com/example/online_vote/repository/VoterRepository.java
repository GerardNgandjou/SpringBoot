package com.example.online_vote.repository;

import com.example.online_vote.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterRepository extends JpaRepository<Registration, Long> {

}