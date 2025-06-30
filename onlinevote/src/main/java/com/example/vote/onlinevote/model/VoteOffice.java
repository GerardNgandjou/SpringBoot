package com.example.vote.onlinevote.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class VoteOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOffice;
    @Column(unique = true)
    private String nameOffice;
    private String locationOffice;
    private String descriptionOffice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "office")
    @JsonManagedReference(value = "office-voter")  // Changed to match the back reference
    private List<Voter> voters;

    public VoteOffice() {}

    public VoteOffice(String nameOffice, String locationOffice, String descriptionOffice, List<Voter> voters) {
        this.nameOffice = nameOffice;
        this.locationOffice = locationOffice;
        this.descriptionOffice = descriptionOffice;
        this.voters = voters;
    }

}

