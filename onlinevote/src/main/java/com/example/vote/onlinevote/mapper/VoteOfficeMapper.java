package com.example.vote.onlinevote.mapper;

import com.example.vote.onlinevote.dto.VoteOfficeDto;
import com.example.vote.onlinevote.model.VoteOffice;
import com.example.vote.onlinevote.model.Voter;
import com.example.vote.onlinevote.repository.VoterRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class VoteOfficeMapper {

    private VoterRepository voterRepository;

    public VoteOfficeDto toVoteOfficeDto(VoteOffice voteOffice) {  // Convert the VoteOfficeDto to VoteOffice
        
        List<Long> votersIds = voteOffice.getVoters()
                .stream()
                .map(Voter::getId)
                .collect(Collectors.toList());

        int votersCount = votersIds.size();

        return new VoteOfficeDto(
                voteOffice.getNameOffice(),
                voteOffice.getLocationOffice(),
                voteOffice.getDescriptionOffice(),
                votersIds,
                votersCount
        );
    }

    public VoteOffice toVoteOffice(VoteOfficeDto voteOfficeDto) {  // Convert the VoteOffice to VoteOfficeDto
        
        List<Voter> voter = voterRepository.findAllById(voteOfficeDto.registerVoterIds());
        return new VoteOffice(
                voteOfficeDto.nameOffice(),
                voteOfficeDto.locationOffice(),
                voteOfficeDto.descriptionOffice(),
                voter
        );
    }

}
