package com.example.vote.onlinevote.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Election {

    @Id
    @GeneratedValue
    private Long idElection;
    private String electionName;
    private String electionDescription;

    @Enumerated(EnumType.STRING)
    private StatusElec electionStatus;

    private Timestamp electionStartDate;
    private Timestamp electionEndDate;

    @ManyToMany(mappedBy = "register", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
//    @JsonManagedReference(value = "user-election")
    private List<User> users = new ArrayList<>();

    public enum  StatusElec {
        ACTIVE, Upcoming, Ended
    }

    public Election() {}

    public Election(
                String electionName,
                String electionDescription,
                StatusElec electionStatus,
                Timestamp electionStartDate,
                Timestamp electionEndDate,
                List<User> users
    ) {
        this.electionName = electionName;
        this.electionDescription = electionDescription;
        this.electionStatus = electionStatus;
        this.electionStartDate = electionStartDate;
        this.electionEndDate = electionEndDate;
        this.users = users;
    }

}
