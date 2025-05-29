package com.example.online_vote.candidate;

import com.example.online_vote.Registration;
import jakarta.persistence.Entity;

@Entity
public class CandidateRegistration extends Registration {

    private String role;

    public CandidateRegistration() {
        super();
    }

    public CandidateRegistration(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
