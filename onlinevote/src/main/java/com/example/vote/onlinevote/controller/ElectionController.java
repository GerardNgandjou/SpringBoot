package com.example.vote.onlinevote.controller;


import com.example.vote.onlinevote.dto.ElectionDto;
import com.example.vote.onlinevote.sevirce.ElectionService;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Getter
public class ElectionController {

    private final ElectionService electionService;

    public ElectionController(ElectionService electionService) {
        this.electionService = electionService;
    }

    @PostMapping("/election/add")
    public ElectionDto addElection(
            @RequestBody ElectionDto election
    ) {
        return electionService.addElection(election);
    }

    @GetMapping("/election/show")
    public ResponseEntity<List<ElectionDto>> showElections() {
        List<ElectionDto> elections = electionService.getAllElections();
        return ResponseEntity.ok(elections);
    }

}
