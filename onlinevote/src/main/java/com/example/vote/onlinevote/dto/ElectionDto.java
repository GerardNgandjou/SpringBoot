package com.example.vote.onlinevote.dto;

import com.example.vote.onlinevote.model.Election;

import java.time.LocalDate;
import java.util.List;

public record ElectionDto(
        String electionName,
        String electionDescription,
        Election.StatusElec electionStatus,
        LocalDate electionStartDate,
        LocalDate electionEndDate,
        List<Long> registeredVoterIds
) {
}
