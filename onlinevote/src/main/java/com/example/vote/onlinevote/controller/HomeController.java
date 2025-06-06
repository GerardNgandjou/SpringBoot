package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.model.Voter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/vote_now")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("voter", new Voter()); // Replace with your actual model class
        return "register";
    }
}
