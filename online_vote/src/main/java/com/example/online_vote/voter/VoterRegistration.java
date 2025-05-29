package com.example.online_vote.voter;

import com.example.online_vote.Registration;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class VoterRegistration extends Registration {

    private String role;

    public VoterRegistration(String role) {
        super();
        this.role = role;
    }

    public VoterRegistration() {

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
