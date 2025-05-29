package com.example.online_vote.controller;

import com.example.online_vote.Registration;
import com.example.online_vote.repository.VoterRepository;
import com.example.online_vote.service.VoterService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/voter")
public class VoterController {

    private final VoterService voterService;
    private final VoterRepository voterRepository;

    public VoterController(VoterService voterService, VoterRepository voterRepository) {
        this.voterService = voterService;
        this.voterRepository = voterRepository;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("voter", new Registration());
        return "register";
    }

    @PostMapping
    public String registerVoter(@Valid @ModelAttribute("voter") Registration registration,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Important: Add the voter object back to the model
            model.addAttribute("voter", registration);
            return "register";
        }

        try {
            voterService.registerVoter(registration);
            redirectAttributes.addFlashAttribute("success", true);
            return "redirect:/register";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("voter", registration);
            return "register";
        }
    }

    public VoterService getVoterService() {
        return voterService;
    }
}