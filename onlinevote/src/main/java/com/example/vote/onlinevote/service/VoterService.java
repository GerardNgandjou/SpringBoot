package com.example.vote.onlinevote.service;

import com.example.vote.onlinevote.dto.VoterDto;
import com.example.vote.onlinevote.dto.VoterResponseDto;
import com.example.vote.onlinevote.exception.ResourceNotFoundException;
import com.example.vote.onlinevote.mapper.VoterMapper;
import com.example.vote.onlinevote.model.Election;
import com.example.vote.onlinevote.model.VoteOffice;
import com.example.vote.onlinevote.model.Voter;
import com.example.vote.onlinevote.repository.ElectionRepository;
import com.example.vote.onlinevote.repository.VoteOfficeRepository;
import com.example.vote.onlinevote.repository.VoterRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoterService {

    private final VoterRepository voterRepository;
    private final VoterMapper voterMapper;
    private final ElectionRepository electionRepository;
    private final VoteOfficeRepository voteOfficeRepository;

    public VoterService(VoterRepository voterRepository, VoterMapper voterMapper, ElectionRepository electionRepository, VoteOfficeRepository voteOfficeRepository) {
        this.voterRepository = voterRepository;
        this.voterMapper = voterMapper;
        this.electionRepository = electionRepository;
        this.voteOfficeRepository = voteOfficeRepository;
    }

    public VoterResponseDto saveVoter(VoterDto voterDto) {
        var voter = voterMapper.toVoter(voterDto);
        var savedVoter = voterRepository.save(voter);
        return voterMapper.toVoterResponseDto(savedVoter);
    }

    public List<VoterResponseDto> showAllVoters() {
        return voterRepository.findAll()
                .stream()
                .map(voterMapper::toVoterResponseDto)
                .collect(Collectors.toList());
    }

    public VoterResponseDto findVoterById(
            Long id
    ){
        return voterRepository.findById(id)
                .map(voterMapper::toVoterResponseDto)
                .orElse(null);
    }

    public List<VoterResponseDto> findVoterByFirstName(
            String firstName
    ){
        return voterRepository.findAllByFirstnameContaining(firstName)
                .stream()
                .map(voterMapper::toVoterResponseDto)
                .collect(Collectors.toList());
    }

     public boolean emailExists(String email) {
        return voterRepository.findByEmail(email).isPresent();
    }

    public void deleteRegistrationById(
            Long id
    ) {
        voterRepository.deleteById(id);
    }

    public Voter getVoterById(Long id) {
        return voterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voter not found with ID: " + id));
    }

    public void registerVoter(Long electionId, VoterDto voterForm) {
       
        List<Election> elections = electionRepository.findAllById(voterForm.registeredElectionIds());

        if (elections.size() != voterForm.registeredElectionIds().size()) {
            throw new EntityNotFoundException("One or more elections not found");
        }

        Voter voter = new Voter();
        voter.setFirstname(voterForm.firstname());
        voter.setLastname(voterForm.firstname());
        voter.setBirthdate(voterForm.birthdate());
        voter.setGender(voterForm.gender());
        voter.setPlaceofbirth(voterForm.placeofbirth());
        voter.setEmail(voterForm.email());
        voter.setLocation(voterForm.location());
        voter.setNumber(voterForm.number());
        voter.setRegion(voterForm.region());
        voter.setDepartment(voterForm.department());
        voter.setArron(voterForm.arron());
        voter.setParty(voterForm.party());
        voter.setCurrentregion(voterForm.currentregion());
        voter.setPollingstation(voterForm.pollingstation());
        
        // voter.setElection(elections);
        voter.setRole(voterForm.role());
        VoteOffice office = voteOfficeRepository.findById(voterForm.officeId())
                .orElseThrow(() -> new EntityNotFoundException("VoteOffice not found with id: " + voterForm.officeId()));
        voter.setOffice(office);

        // voter.setEmail(voterForm.getEmail());
        // voter.setAddress(voterForm.getAddress());
        // voter.setDateOfBirth(voterForm.getDateOfBirth());
        // 
        
        voterRepository.save(voter);
    }

}
