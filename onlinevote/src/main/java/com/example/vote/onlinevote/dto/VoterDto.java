package com.example.vote.onlinevote.dto;

import com.example.vote.onlinevote.model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for exposing Voter entity data without exposing internal structure
 * Contains all user fields plus voter-specific relationships
 */
public record VoterDto(
        @NotEmpty
        @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
        @Pattern(regexp = "[A-Za-z\\s\\-']+", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
        String firstname,
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
        Long officeId   // Just the ID of VoteOffice to avoid recursion
) {}

