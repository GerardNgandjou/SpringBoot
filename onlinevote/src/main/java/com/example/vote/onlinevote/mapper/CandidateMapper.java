package com.example.vote.onlinevote.mapper;

import com.example.vote.onlinevote.dto.CandidateDto;
import com.example.vote.onlinevote.dto.CandidateResponseDto;
import com.example.vote.onlinevote.model.Candidate;
import com.example.vote.onlinevote.model.Election;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateMapper {
    
    public CandidateResponseDto  candidateToResponseDto(Candidate candidate) {
        return new CandidateResponseDto(
                candidate.getFirstname(),
                candidate.getLastname(),
                candidate.getBirthdate(),
                candidate.getGender(),
                candidate.getPlaceofbirth(),
                candidate.getLocation(),
                candidate.getRegion(),
                candidate.getDepartment(),
                candidate.getArron(),
                candidate.getParty(),
                candidate.getRole()
        );
    }
    
    public Candidate candidateDtoToCandidate(CandidateDto candidateDto) {
        var candidate = new Candidate();
        candidate.setFirstname(candidateDto.firstname());
        candidate.setLastname(candidateDto.lastname());
        candidate.setBirthdate(candidateDto.birthdate());
        candidate.setGender(candidateDto.gender());
        candidate.setPlaceofbirth(candidateDto.placeofbirth());
        candidate.setEmail(candidateDto.email());
        candidate.setLocation(candidateDto.location());
        candidate.setNumber(candidateDto.number());
        candidate.setRegion(candidateDto.region());
        candidate.setDepartment(candidateDto.department());
        candidate.setArron(candidateDto.arron());
        candidate.setParty(candidateDto.party());
        candidate.setCurrentregion(candidateDto.currentregion());

        // Properly convert IDs to Election list
        List<Election> elections = new ArrayList<>();
        for (Long id : candidateDto.registeredElectionIds()) {
            Election election = new Election();
            election.setIdElection(id);
            elections.add(election);
        }
        candidate.setRegister(elections);
        candidate.setRole(candidateDto.role());
        candidate.setDeposit(candidateDto.deposit());
        candidate.setScore(candidate.getScore());

        return candidate;
    }
}
