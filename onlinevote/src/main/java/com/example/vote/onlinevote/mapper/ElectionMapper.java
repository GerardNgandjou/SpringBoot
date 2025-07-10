package com.example.vote.onlinevote.mapper;

import com.example.vote.onlinevote.dto.ElectionDto;
import com.example.vote.onlinevote.model.Election;
import com.example.vote.onlinevote.model.User;
import com.example.vote.onlinevote.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionMapper {

    private UserRepository userRepository;

    public ElectionDto toElectionDto(Election election) {  // Convert the ElectionDto to Election

        List<Long> userIds = election.getUsers()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());
        return new ElectionDto(
                election.getElectionName(),
                election.getElectionDescription(),
                election.getElectionStatus(),
                election.getElectionStartDate(),
                election.getElectionEndDate(),
                userIds
        );
    }

    public Election toElection(ElectionDto electionDto) {
        
        List<User> users = userRepository.findAllById(electionDto.registeredVoterIds());
        return new Election(
                electionDto.electionName(),
                electionDto.electionDescription(),
                electionDto.electionStatus(),
                electionDto.electionStartDate(),
                electionDto.electionEndDate(),
                users
        );
    }

}
