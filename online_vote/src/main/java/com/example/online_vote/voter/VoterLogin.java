package com.example.online_vote.voter;

import com.example.online_vote.Login;
import jakarta.persistence.Entity;

@Entity
public class VoterLogin extends Login {

    private String role;

    public VoterLogin(String role) {
        super();
        this.role = role;
    }

    public VoterLogin() {

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
