package com.example.vote.onlinevote.mapper;

import com.example.vote.onlinevote.dto.ElectionDto;
import com.example.vote.onlinevote.model.Election;
import com.example.vote.onlinevote.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionMapper {

    public ElectionDto toElectionDto(Election election) {  // Convert the ElectionDto to Election

        List<Long> userIds = election.getUsers()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return new ElectionDto(
                election.getElectionName(),
                election.getElectionStatus(),
                election.getElectionDescription(),
                election.getElectionStartDate(),
                election.getElectionEndDate(),
                userIds
        );
    }

    public Election toElection(ElectionDto electionDto) {  // Convert the Election to ElectionDto
        return new Election(
                electionDto.electionName(),
                electionDto.electionStatus(),
                electionDto.electionDescription(),
                electionDto.electionStartDate(),
                electionDto.electionEndDate()
        );
    }
}
