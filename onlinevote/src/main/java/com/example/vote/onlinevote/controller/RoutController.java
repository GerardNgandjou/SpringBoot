package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.model.Voter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoutController {

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

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("voter", new Voter());
        return "register";
    }
}
