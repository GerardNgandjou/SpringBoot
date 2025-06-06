package com.example.vote.onlinevote.controller;


import com.example.vote.onlinevote.model.Election;
import com.example.vote.onlinevote.repository.ElectionRepository;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Getter
public class ElectionController {

    private final ElectionRepository electionRepository;

    public ElectionController(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    @PostMapping("/election/add")
    public Election addElection(
            @RequestBody Election election
    ) {
        return electionRepository.save(election);
    }

    @GetMapping("/election/show")
    public List<Election> showElections() {
        return electionRepository.findAll();
    }
}
