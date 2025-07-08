package com.example.vote.onlinevote.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@Table(name = "user_app")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String gender;
    private String placeofbirth;
    @Column(unique = true)
    private String email;
    private String location;
    private Integer number;
    private String region;
    private String department;
    private String arron;
    private String party;
    private String currentregion;
    private String pollingstation;

    @ManyToMany
    @JoinTable(
            name = "election_user",
            joinColumns = @JoinColumn(name = "user_id"),  // FK to user
            inverseJoinColumns = @JoinColumn(name = "election_id")
    )
    private List<Election> register = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(String firstname, String lastname, LocalDate birthdate, String gender, String email, Integer phoneNumber, String region, String department, String arron, String politicalParty, String currentRegion, String pollingStation, List<Election> register, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
        this.number = phoneNumber;
        this.region = region;
        this.department = department;
        this.arron = arron;
        this.party = politicalParty;
        this.pollingstation = pollingStation;
        this.currentregion = currentRegion;
        this.register = register;
        this.role = role;
    }

    public enum Role {
        VOTER, CANDIDATE, ADMIN
    }

}
