package com.example.vote.onlinevote.dto;

import java.util.List;

public record VoteOfficeDto(
        String nameOffice,
        String locationOffice,
        String descriptionOffice,
        List<Long> registerVoterIds,
        int countVoter
) {
}
