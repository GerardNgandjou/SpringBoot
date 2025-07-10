package com.example.vote.onlinevote.dto;

import com.example.vote.onlinevote.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

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

        @NotEmpty
        @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
        @Pattern(regexp = "[A-Za-z\\s\\-']+", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
        String lastname,

        @NotNull(message = "Birth date is required")
        @Past(message = "Birthdate must be in the past")
        LocalDate birthdate,

        @NotBlank(message = "Gender is required")
        String gender,

        String placeofbirth,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Column(unique = true)
        String email,

        @NotBlank(message = "Location is required")
        @Size(min = 3, max = 50, message = "Location must be between 3 and 50 characters")
        String location,

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[6-9][0-9]{8}$", message = "Phone number must be 9 digits starting with 6-9")
        Integer number,

        @NotBlank(message = "Region is required")
        @Size(min = 3, max = 50, message = "Region must be between 3 and 50 characters")
        String region,

        @NotBlank(message = "Department is required")
        @Size(min = 3, max = 50, message = "Department must be between 3 and 50 characters")
        String department,

        @NotBlank(message = "Arrondissement is required")
        @Size(min = 3, max = 50, message = "Arrondissement must be between 3 and 50 characters")
        String arron,

        @NotBlank(message = "Political party is required")
        String party,

        @NotBlank(message = "Current region is required")
        String currentregion,

        @NotBlank(message = "Polling station is required")
        String pollingstation,

        List<Long> registeredElectionIds,  // Just IDs from register list

        User.Role role,

        Long officeId   // Just the ID of VoteOffice to avoid recursion
) {

}

