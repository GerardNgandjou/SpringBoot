package com.example.vote.onlinevote.sevirce;

import com.example.vote.onlinevote.model.Voter;
import com.example.vote.onlinevote.repository.VoterRepository;
import org.springframework.stereotype.Service;

@Service
public class VoterService {

    private final VoterRepository voterRepository;

    public VoterService(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    public Voter save(Voter voter) {
        // Additional business logic can be added here
        return voterRepository.save(voter);
    }
}