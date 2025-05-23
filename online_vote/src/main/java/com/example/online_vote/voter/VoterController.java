package com.example.online_vote.voter;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voter")
public class VoterController {

    private final VoterRepository voterRepository;

    public VoterController(VoterRepository voterRepository) {
        this.voterRepository = voterRepository;
    }

    @PostMapping("/add")
    public VoterLogin add(@RequestBody VoterLogin voterLogin) {
        return voterRepository.save(voterLogin);
    }

    public VoterRepository getVoterRepository() {
        return voterRepository;
    }
}
