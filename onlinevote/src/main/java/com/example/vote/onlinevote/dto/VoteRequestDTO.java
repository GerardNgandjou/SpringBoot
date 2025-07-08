package com.example.vote.onlinevote.dto;

import jakarta.validation.constraints.NotNull;

public record VoteRequestDTO(
    @NotNull(message = "Election ID is required")
    Long electionId,
    
    @NotNull(message = "Candidate ID is required")
    Long candidateId,
    
    @NotNull(message = "Voter ID is required")
    Long voterId
) {}