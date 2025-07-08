package com.example.vote.onlinevote.dto;

import com.example.vote.onlinevote.model.User;

import java.time.LocalDate;

public record VoterResponseDto(
        String firstname,
        String lastname,
        LocalDate birthdate,
        String gender,
        String placeofbirth,
//        String email,
        String location,
        Integer number,
        String region,
        String department,
        String arron,
        String party,
//        String currentregion,
//        String pollingstation,
//        List<Long> registeredElectionIds,  // Just IDs from register list
        User.Role role
//        Long officeId   // Just the ID of VoteOffice to avoid recursion
) {
}
