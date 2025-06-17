package com.example.vote.onlinevote.mapper;

import com.example.vote.onlinevote.dto.VoteOfficeDto;
import com.example.vote.onlinevote.model.VoteOffice;
import org.springframework.stereotype.Service;

@Service
public class VoteOfficeMapper {

    public VoteOfficeDto toVoteOfficeDto(VoteOffice voteOffice) {  // Convert the VoteOfficeDto to VoteOffice
        return new VoteOfficeDto(
                voteOffice.getNameOffice(),
                voteOffice.getLocationOffice(),
                voteOffice.getDescriptionOffice()
        );
    }

    public VoteOffice toVoteOffice(VoteOfficeDto voteOfficeDto) {  // Convert the VoteOffice to VoteOfficeDto
        return new VoteOffice(
                voteOfficeDto.nameOffice(),
                voteOfficeDto.locationOffice(),
                voteOfficeDto.descriptionOffice()
        );
    }

}
