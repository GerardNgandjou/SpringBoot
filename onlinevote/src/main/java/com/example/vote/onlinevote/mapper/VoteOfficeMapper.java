package com.example.vote.onlinevote.mapper;

import com.example.vote.onlinevote.dto.VoteOfficeDto;
import com.example.vote.onlinevote.model.VoteOffice;
import org.springframework.stereotype.Service;

@Service
public class VoteOfficeMapper {

    public VoteOfficeDto toVoteOfficeDto(VoteOffice voteOffice) {
        return new VoteOfficeDto(
                voteOffice.getNameOffice(),
                voteOffice.getLocationOffice(),
                voteOffice.getDescriptionOffice()
        );
    }

    public VoteOffice toVoteOffice(VoteOfficeDto voteOfficeDto) {
        return new VoteOffice(
                voteOfficeDto.nameOffice(),
                voteOfficeDto.locationOffice(),
                voteOfficeDto.descriptionOffice()
        );
    }

}
