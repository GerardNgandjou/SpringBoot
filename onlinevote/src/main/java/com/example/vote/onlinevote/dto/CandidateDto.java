package com.example.vote.onlinevote.dto;

import com.example.vote.onlinevote.model.User;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record CandidateDto(
        @NotEmpty
        String firstname,
        @NotEmpty
        String lastname,
        LocalDate birthdate,
        String gender,
        String placeofbirth,
        String email,
        String location,
        Integer number,
        String region,
        String department,
        String arron,
        String party,
        String currentregion,
        String pollingstation,
        List<Long> registeredElectionIds,  // Just IDs from register list
        User.Role role,
        Float deposit,
        Integer Score
) {
}
