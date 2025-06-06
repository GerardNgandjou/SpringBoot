package com.example.vote.onlinevote.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne
    @JoinColumn(name = "voter_id")
    private Voter voter;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private String voteContent;
    private Timestamp voteTime;
    
    public Vote() {}

    public Vote(Long voteId, Voter voter, Candidate candidate, String voteContent, Timestamp voteTime) {
        this.voteId = voteId;
//        this.voter = voter;
//        this.candidate = candidate;
        this.voteContent = voteContent;
        this.voteTime = voteTime;
    }
}
