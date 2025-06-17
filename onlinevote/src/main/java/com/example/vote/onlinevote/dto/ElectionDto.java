package com.example.vote.onlinevote.dto;

import java.time.LocalDate;

public record ElectionDto(
        String electionName,
        String electionDescription,
        String electionStatus,
        LocalDate electionStartDate,
        LocalDate electionEndDate
) {
}
