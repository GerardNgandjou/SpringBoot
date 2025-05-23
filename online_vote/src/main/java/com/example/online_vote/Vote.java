package com.example.online_vote;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer voteId;
    private Integer voterId;
    private Integer candidateId;
    private Timestamp voteDate;

    public Vote(Integer voterId, Integer candidateId, Timestamp voteDate) {
        this.voterId = voterId;
        this.candidateId = candidateId;
        this.voteDate = voteDate;
    }

    public Vote() {

    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public Integer getVoterId() {
        return voterId;
    }

    public void setVoterId(Integer voterId) {
        this.voterId = voterId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Timestamp getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Timestamp voteDate) {
        this.voteDate = voteDate;
    }
}
