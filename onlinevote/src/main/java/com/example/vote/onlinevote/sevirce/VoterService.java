package com.example.vote.onlinevote.sevirce;

import com.example.vote.onlinevote.dto.VoterDto;
import com.example.vote.onlinevote.dto.VoterResponseDto;
import com.example.vote.onlinevote.exception.ResourceNotFoundException;
import com.example.vote.onlinevote.mapper.VoterMapper;
import com.example.vote.onlinevote.model.Voter;
import com.example.vote.onlinevote.repository.VoterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoterService {

    private final VoterRepository voterRepository;
    private final VoterMapper voterMapper;

    public VoterService(VoterRepository voterRepository, VoterMapper voterMapper) {
        this.voterRepository = voterRepository;
        this.voterMapper = voterMapper;
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

    public void deleteRegistrationById(
            Long id
    ) {
        voterRepository.deleteById(id);
    }

    public Voter getVoterById(Long id) {
        return voterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voter not found with ID: " + id));
    }

}
