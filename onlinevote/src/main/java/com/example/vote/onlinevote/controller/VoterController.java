package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.model.Voter;
import com.example.vote.onlinevote.repository.VoterRepository;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Getter
@RestController
public class VoterController {

    private final VoterRepository voterRepository;

    public VoterController(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @PostMapping("/voter/register")
    public Voter register(@RequestBody Voter voter) {
        return voterRepository.save(voter);
    }

    @GetMapping("/voter/show")
    public List<Voter> show() {
        return voterRepository.findAll();
    }
}
