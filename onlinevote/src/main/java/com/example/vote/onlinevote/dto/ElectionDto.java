package com.example.vote.onlinevote.dto;

import java.time.LocalDate;
import java.util.List;

public record ElectionDto(
        String electionName,
        String electionDescription,
        String electionStatus,
        LocalDate electionStartDate,
        LocalDate electionEndDate,
        List<Long> registeredVoterIds
) {
}
