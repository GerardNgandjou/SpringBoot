package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.model.Candidate;
import com.example.vote.onlinevote.repository.CandidateRepository;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Getter
public class CandidateController {

    private final CandidateRepository candidateRepository;

    public CandidateController(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @PostMapping("/candidate/add")
    public Candidate add(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @GetMapping("/candidate/display")
    public List<Candidate> display() {
        return candidateRepository.findAll();
    }

    @GetMapping("/candidate/find/{id}")  // Get User with her id
    public Candidate getcandidateById(
            @PathVariable Long id
    ){
        return candidateRepository.findById(id)
                .orElse(new Candidate());
    }

    @GetMapping("/candidate/search/{candidate_name}")  // et User with her name
    public List<Candidate> findRegistrationsByFirstName(
            @PathVariable("candidate_name") String firstName
    ){
        return candidateRepository.findAllByFirstnameContaining(firstName);
    }

    @DeleteMapping("/candidate/delete/{vote-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRegistrationById(
            @PathVariable("vote-id") Long id
    ) {
        candidateRepository.deleteById(id);
    }
}
