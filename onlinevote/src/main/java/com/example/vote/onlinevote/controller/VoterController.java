package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.model.Voter;
import com.example.vote.onlinevote.repository.VoterRepository;
import com.example.vote.onlinevote.sevirce.VoterService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Controller
@RequestMapping
public class VoterController {

//    @Autowired
    private final VoterRepository voterRepository;

    private final VoterService voterService;

    public VoterController(VoterRepository voterRepository, VoterService voterService) {
        this.voterRepository = voterRepository;
        this.voterService = voterService;
    }

//    @PostMapping("/voter/register")
//    public Voter register(@RequestBody Voter voter) {
//        return voterRepository.save(voter);
//    }

    @PostMapping("/register")
    public String processRegistration(@Valid @ModelAttribute("voter") Voter voter,
                                      BindingResult result,
                                      Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        // Save voter to database
        voterService.save(voter);

        return "redirect:/success";
    }

    @GetMapping("/voter/register")
    public List<Voter> show() {
        return voterRepository.findAll();
    }

    @GetMapping("/voter/find/{id}")  // Get User with her id
    public Voter getVoterById(
            @PathVariable Long id
    ){
        return voterRepository.findById(id)
                .orElse(new Voter());
    }

    @GetMapping("/voter/search/{voter_name}")  // et User with her name
    public List<Voter> findRegistrationsByFirstName(
            @PathVariable("voter_name") String firstName
    ){
        return voterRepository.findAllByFirstnameContaining(firstName);
    }

    @DeleteMapping("/voter/delete/{vote-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRegistrationById(
            @PathVariable("vote-id") Long id
    ) {
        voterRepository.deleteById(id);
    }
}
