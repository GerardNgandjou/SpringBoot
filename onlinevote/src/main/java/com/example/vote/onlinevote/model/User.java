package com.example.vote.onlinevote.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String gender;
    @Column(unique = true)
    private String email;
    private Integer number;
    private String region;
    private String department;
    private String arron;
    private String party;

    public Registration() {
    }

    public Registration(String firstname, String lastname, LocalDate birthdate, String gender, String email, Integer phoneNumber, String region, String department, String arron, String politicalParty, Election register) {
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
    }

}
