package com.example.vote.onlinevote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("CANDIDATE")
@AllArgsConstructor
public class Candidate extends User {

    private Float deposit;
    private Integer score;

    public Candidate() {
        super();
    }

    public boolean isRegisteredForElection(Long electionId) {
        return this.getRegister().stream()
            .anyMatch(e -> e.getIdElection().equals(electionId));
    }

}
