package com.example.online_vote;

import java.util.Date;

import jakarta.persistence.*;

@Entity 
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer matricule;
    @Column(nullable = false, length = 50)
    protected String firstName;
    @Column(nullable = false, length = 50)
    protected String surName;
    protected Date birthDate;
    @Column(nullable = false, length = 20)
    protected String placeOfBirth;
    @Column(nullable = false, length = 50, unique = true)
    protected String email;
    @Column(nullable = false, length = 9, unique = true)
    protected Integer phoneNumber;
    @Column(nullable = false, length = 20)
    protected String regionNais;
    @Column(nullable = false, length = 20)
    protected String departNais;
    @Column(nullable = false, length = 20)
    protected String arrondNais;
    @Column(nullable = false, length = 20)
    protected String partiPolitique;

    public Login(String surName, String firstName, Date birthDate, String placeOfBirth, String email, Integer phoneNumber, String regionNais, String departNais, String arrondNais, String partiPolitique) {
        this.surName = surName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.placeOfBirth = placeOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.regionNais = regionNais;
        this.departNais = departNais;
        this.arrondNais = arrondNais;
        this.partiPolitique = partiPolitique;
    }

    public Login() {

    }
    
    public Integer getMatricule() {
        return matricule;
    }

    public void setMatricule(Integer matricule) {
        this.matricule = matricule;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegionNais() {
        return regionNais;
    }

    public void setRegionNais(String regionNais) {
        this.regionNais = regionNais;
    }

    public String getDepartNais() {
        return departNais;
    }

    public void setDepartNais(String departNais) {
        this.departNais = departNais;
    }

    public String getArrondNais() {
        return arrondNais;
    }

    public void setArrondNais(String arrondNais) {
        this.arrondNais = arrondNais;
    }

    public String getPartiPolitique() {
        return partiPolitique;
    }

    public void setPartiPolitique(String partiPolitique) {
        this.partiPolitique = partiPolitique;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
