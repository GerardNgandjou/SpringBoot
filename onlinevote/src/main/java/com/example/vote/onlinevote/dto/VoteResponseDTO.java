package com.example.vote.onlinevote.dto;

import java.time.LocalDateTime;

public record VoteResponseDTO(
    Long voteId,
    String candidateName,
    String electionTitle,
    LocalDateTime timestamp
) {

}
