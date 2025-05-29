package com.example.online_vote;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The Login entity represents user login credentials in the system.
 * It stores username/password combinations and supports JWT authentication.
 */
@Entity
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Username is required")
    @Column(unique = true) // Ensures username uniqueness in the database
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    // JWT-specific fields (not stored in DB)
    @Transient
    private String token;

    /**
     * Default constructor required by JPA.
     */
    public Login() {}

    /**
     * Constructor for login operations.
     */
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // --- Getters and Setters ---
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}