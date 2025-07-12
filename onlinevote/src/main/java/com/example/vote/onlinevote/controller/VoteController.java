package com.example.vote.onlinevote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.vote.onlinevote.model.Candidate;
import com.example.vote.onlinevote.model.Election;
import com.example.vote.onlinevote.model.Voter;
import com.example.vote.onlinevote.repository.CandidateRepository;
import com.example.vote.onlinevote.repository.ElectionRepository;
import com.example.vote.onlinevote.repository.VoterRepository;
import com.example.vote.onlinevote.service.VoteService;


@Controller
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @PostMapping
    public String castVote(@RequestParam Long voterId,
                           @RequestParam Long candidateId,
                           @RequestParam Long electionId,
                           Model model) {
        Voter voter = voterRepository.findById(voterId).orElseThrow();
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow();
        Election election = electionRepository.findById(electionId).orElseThrow();

        try {
            voteService.castVote(voter, candidate, election);
            model.addAttribute("message", "Vote cast successfully!");
        } catch (IllegalStateException ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "vote-result"; // create vote-result.html
    }
}
