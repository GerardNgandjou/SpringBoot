package com.example.vote.onlinevote.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "votes", indexes = {
        @Index(name = "idx_vote_voter_election", columnList = "voter_id, election_id", unique = true),
        @Index(name = "idx_vote_election", columnList = "election_id"),
        @Index(name = "idx_vote_candidate", columnList = "candidate_id")
})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;

    @ManyToOne
    // @JoinColumn(name = "voter_id", nullable = false)
    private Voter voter;

    @ManyToOne
    // @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    // @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @Column(nullable = false)
    private String voteContent;

    @Column(nullable = false)
    private Timestamp voteTime;
}
