package com.example.online_vote.candidate;

import com.example.online_vote.Login;
import jakarta.persistence.Entity;

@Entity
public class CandidateLogin extends Login {

    private String role;

    public CandidateLogin() {
        super();
    }

    public CandidateLogin(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
