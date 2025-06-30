package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.model.LoginRequest;
import com.example.vote.onlinevote.model.ResourceNotFoundException;
import com.example.vote.onlinevote.model.Voter;
import com.example.vote.onlinevote.sevirce.VoterService;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoutController {

    private final VoterService voterService;

    public RoutController(VoterService voterService) {
        this.voterService = voterService;
    }

    @GetMapping("/")
    public String home() {
        return "index"; // This will return index.html from templates folder
        // For static index.html, you don't even need this controller
    }

    // Or for combined page
    @GetMapping("/auth")
    public String showAuthPage() {
        return "auth"; // combined login/register page
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        // Your authentication logic here
        boolean authenticated = false; // TODO: Replace with actual authentication logic
        // Example: authenticated = voterService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (authenticated) {
            return ResponseEntity.ok()
                .body(Map.of(
                    "status", "success",
                    "redirectUrl", "/dash"
                ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                    "error", "Invalid credentials"
                ));
        }
    }

    // @GetMapping("/voter/add")
    // public String showRegisterPage() {
    //     return "registration";
    // }

    @GetMapping("/display_election")
    public String displayElections() {
        // You can add any model attributes if needed
        return "display_election"; // This should match your Thymeleaf template name
    }

    @PostMapping("/voters/{id}/send-verification")
    @ResponseBody
    public ResponseEntity<?> sendVerificationEmail(@PathVariable Long id) {
        try {
            Voter voter = voterService.getVoterById(id);
            if (voter == null) {
                return ResponseEntity.notFound().build();
            }

//            // INTERNAL_SERVER_ERRORmplement email sending logic here
//            emailService.sendVerificationEmail(
//                    voter.getEmail(),
//                    "Voter Registration Verification",
//                    "Your voter registration has been verified. Thank you!"
//            );

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/voters/{id}/print")
    public String getPrintTemplate(@PathVariable Long id, Model model) {
        Voter voter = voterService.getVoterById(id);
        if (voter == null) {
            throw new ResourceNotFoundException("Voter not found");
        }

        model.addAttribute("voter", voter);
        return "print_voter"; // You'll need to create this Thymeleaf template
    }
}
