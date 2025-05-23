package com.example.online_vote;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Regitration {

    @Id
    protected Integer id;
    protected String email;
    protected String password;
    
    public Regitration() {
        
    }
    
    public Regitration(Integer id, String email, String password) {
        this.email = email;
        this.id = id;
        this.password = password;
    }
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
