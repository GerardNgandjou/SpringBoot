package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.model.VoteOffice;
import com.example.vote.onlinevote.repository.VoteOfficeRepository;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Getter
@RestController
public class VoteOfficeController {
    
    private final VoteOfficeRepository voteOfficeRepository;

    public VoteOfficeController(VoteOfficeRepository voteOfficeRepository) {
        this.voteOfficeRepository = voteOfficeRepository;
    }

    @PostMapping("/vote_office/set")
    public VoteOffice addVoteOffice(
            @RequestBody VoteOffice VoteOffice
    ) {
        return voteOfficeRepository.save(VoteOffice);
    }

    @GetMapping("/vote_office/get")
    public List<VoteOffice> showVoteOffices() {
        return voteOfficeRepository.findAll();
    }
}
