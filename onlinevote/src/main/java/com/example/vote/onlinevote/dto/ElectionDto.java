package com.example.vote.onlinevote.dto;

import com.example.vote.onlinevote.model.Election;

import java.sql.Timestamp;
import java.util.List;

public record ElectionDto(
        String electionName,
        String electionDescription,
        Election.StatusElec electionStatus,
        Timestamp electionStartDate,
        Timestamp electionEndDate,
        List<Long> registeredVoterIds
) {
}
