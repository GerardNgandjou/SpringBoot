package com.example.online_vote.service;

import com.example.online_vote.Registration;
import com.example.online_vote.repository.VoterRepository;
import org.springframework.stereotype.Service;

@Service
public class VoterService {

    private final VoterRepository voterRepository;

    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    public Registration registerVoter(Registration voter) {
        // Additional business logic can be added here
        return voterRepository.save(voter);
    }
}