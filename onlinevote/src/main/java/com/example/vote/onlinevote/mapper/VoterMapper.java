package com.example.vote.onlinevote.mapper;

import com.example.vote.onlinevote.dto.VoterDto;
import com.example.vote.onlinevote.dto.VoterResponseDto;
import com.example.vote.onlinevote.model.Election;
import com.example.vote.onlinevote.model.VoteOffice;
import com.example.vote.onlinevote.model.Voter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoterMapper {

    public Voter toVoter(VoterDto voterDto) {
        var voter = new Voter();
        voter.setFirstname(voterDto.firstname());
        voter.setLastname(voterDto.lastname());
        voter.setBirthdate(voterDto.birthdate());
        voter.setGender(voterDto.gender());
        voter.setPlaceofbirth(voterDto.placeofbirth());
        voter.setEmail(voterDto.email());
        voter.setLocation(voterDto.location());
        voter.setNumber(voterDto.number());
        voter.setRegion(voterDto.region());
        voter.setDepartment(voterDto.department());
        voter.setArron(voterDto.arron());
        voter.setParty(voterDto.party());
        voter.setCurrentregion(voterDto.currentregion());
        voter.setPollingstation(voterDto.pollingstation());

        // Properly convert IDs to Election list
        List<Election> elections = new ArrayList<>();
        for (Long id : voterDto.registeredElectionIds()) {
            Election election = new Election();
            election.setIdElection(id);
            elections.add(election);
        }
        voter.setRegister(elections);


        voter.setRole(voterDto.role());

        var voteOffice = new VoteOffice();
        voteOffice.setIdOffice(voterDto.officeId());
        voter.setOffice(voteOffice);

        return voter;
    }

    public VoterResponseDto toVoterResponseDto(Voter voter) {
        return new VoterResponseDto(
                voter.getFirstname(),
                voter.getLastname(),
                voter.getBirthdate(),
                voter.getGender(),
                voter.getPlaceofbirth(),
                voter.getLocation(),
                voter.getNumber(),
                voter.getRegion(),
                voter.getDepartment(),
                voter.getArron(),
                voter.getParty(),
                voter.getRole()
        );
    }
}
