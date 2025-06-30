package com.example.vote.onlinevote.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String email;
    private String password;
    private boolean rememberMe;

    public LoginRequest() {
        // Default constructor
    }
    public LoginRequest(String email, String password, boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;   
    }

    
}
