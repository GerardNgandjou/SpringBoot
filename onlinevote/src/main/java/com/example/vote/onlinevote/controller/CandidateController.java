package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.dto.CandidateDto;
import com.example.vote.onlinevote.dto.CandidateResponseDto;
import com.example.vote.onlinevote.service.CandidateService;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Getter
public class CandidateController {

    private  final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping("/candidate/add")
    public CandidateResponseDto add(
            @RequestBody CandidateDto candidateDto
    ) {
        return candidateService.savedCandidate(candidateDto);
    }

    @GetMapping("/candidate/display")
    public List<CandidateResponseDto> display() {
        return candidateService.findAllCandidates();
    }

    @GetMapping("/candidate/find/{id}")  // Get User with her id
    public CandidateResponseDto getCandidateById(
            @PathVariable Long id
    ){
        return candidateService.findCandidateById(id);
    }

    @GetMapping("/candidate/search/{candidate_name}")  // et User with her name
    public List<CandidateResponseDto> findRegistrationsByFirstName(
            @PathVariable("candidate_name") String firstName
    ){
        return candidateService.findCandidateByFirstname(firstName);
    }

    @DeleteMapping("/candidate/delete/{vote-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRegistrationById(
            @PathVariable("vote-id") Long id
    ) {
        candidateService.deleteCandidateById(id);
    }
}
