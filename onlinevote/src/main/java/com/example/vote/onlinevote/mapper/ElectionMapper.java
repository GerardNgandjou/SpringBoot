package com.example.vote.onlinevote.mapper;

import com.example.vote.onlinevote.dto.ElectionDto;
import com.example.vote.onlinevote.model.Election;
import org.springframework.stereotype.Service;

@Service
public class ElectionMapper {

    public ElectionDto toElectionDto(Election Election) {  // Convert the ElectionDto to Election
        return new ElectionDto(
                Election.getElectionName(),
                Election.getElectionStatus(),
                Election.getElectionDescription(),
                Election.getElectionStartDate(),
                Election.getElectionEndDate()
        );
    }

    public Election toElection(ElectionDto ElectionDto) {  // Convert the Election to ElectionDto
        return new Election(
                ElectionDto.electionName(),
                ElectionDto.electionStatus(),
                ElectionDto.electionDescription(),
                ElectionDto.electionStartDate(),
                ElectionDto.electionEndDate()
        );
    }
}
