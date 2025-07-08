package com.example.vote.onlinevote.service;
// package com.example.vote.onlinevote.sevirce;

// import com.example.vote.onlinevote.dto.VoteRequestDTO;
// import com.example.vote.onlinevote.dto.VoteResponseDTO;
// import com.example.vote.onlinevote.exception.DuplicateVoteException;
// import com.example.vote.onlinevote.exception.ElectionEndedException;
// import com.example.vote.onlinevote.exception.ElectionNotStartedException;
// import com.example.vote.onlinevote.exception.ForbiddenException;
// import com.example.vote.onlinevote.exception.InvalidCandidateException;
// import com.example.vote.onlinevote.exception.ResourceNotFoundException;
// import com.example.vote.onlinevote.exception.UnauthorizedException;
// import com.example.vote.onlinevote.model.*;
// import com.example.vote.onlinevote.repository.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.sql.Timestamp;
// import java.time.LocalDateTime;

// @Service
// public class VoteService {

//     private final VoteRepository voteRepository;
//     private final CandidateRepository candidateRepository;
//     private final ElectionRepository electionRepository;
//     private final VoterRepository voterRepository;

//     @Autowired
//     public VoteService(VoteRepository voteRepository,
//                      CandidateRepository candidateRepository,
//                      ElectionRepository electionRepository,
//                      VoterRepository voterRepository) {
//         this.voteRepository = voteRepository;
//         this.candidateRepository = candidateRepository;
//         this.electionRepository = electionRepository;
//         this.voterRepository = voterRepository;
//     }

//     @Transactional
//     public VoteResponseDTO castVote(VoteRequestDTO voteRequest, String voterEmail) {
//         // Check if the voter exists
//         if (voterEmail == null || voterEmail.isEmpty()) {
//             throw new UnauthorizedException("Voter email is required to cast a vote");
//         }
//         // Validate the vote request
//         Election election = electionRepository.findById(voteRequest.electionId())
//             .orElseThrow(() -> new ResourceNotFoundException("Election not found with id: " + voteRequest.electionId()));
        
//         Candidate candidate = candidateRepository.findById(voteRequest.candidateId())
//             .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + voteRequest.candidateId()));
            
//         Voter voter = voterRepository.findById(voteRequest.voterId())
//             .orElseThrow(() -> new ResourceNotFoundException("Voter not found with id: " + voteRequest.voterId()));

//         // Verify voter owns this account
//         if (!voter.getEmail().equals(voterEmail)) {
//             throw new UnauthorizedException("You can only vote with your own account");
//         }

//         // Check voter approval status
//         if (!"APPROVED".equals(voter.getStatusVoter())) {
//             throw new ForbiddenException("Only approved voters can cast votes. Your status: " + voter.getStatusVoter());
//         }

//         // Check election dates
//         LocalDateTime now = LocalDateTime.now();
//         if (now.isBefore(election.getElectionStartDate().toLocalDateTime())) {
//             throw new ElectionNotStartedException("Voting for " + election.getElectionName() + " hasn't started yet");
//         }
//         if (now.isAfter(election.getElectionEndDate().toLocalDateTime())) {
//             throw new ElectionEndedException("Voting for " + election.getElectionName() + " has already ended");
//         }

//         // Check if candidate belongs to this election
//         if (!candidate.isRegisteredForElection(election.getIdElection())) {
//             throw new InvalidCandidateException("Candidate doesn't belong to this election");
//         }

//         // Check if voter has already voted in this election
//         if (voteRepository.existsByVoterAndElection(voter, election)) {
//             throw new DuplicateVoteException("You have already voted in this election");
//         }

//         // Create and save the vote
//         Vote vote = new Vote();
//         vote.setElection(election);
//         vote.setCandidate(candidate);
//         vote.setVoter(voter);
//         vote.setVoteTime(Timestamp.valueOf(now));
//         vote.setVoteContent("Vote for " + candidate.getFirstname() + " " + candidate.getLastname() + 
//                           " in " + election.getElectionName());
        
//         Vote savedVote = voteRepository.save(vote);

//         // Prepare response
//         return new VoteResponseDTO(
//             savedVote.getVoteId(),
//             candidate.getFirstname() + " " + candidate.getLastname(),
//             election.getElectionName(),
//             savedVote.getVoteTime().toLocalDateTime()
//         );
//     }

//     // Additional method to check voting eligibility
//     public boolean isEligibleToVote(Long voterId, Long electionId) {
//         Voter voter = voterRepository.findById(voterId)
//             .orElseThrow(() -> new ResourceNotFoundException("Voter not found"));
        
//         if (!"APPROVED".equals(voter.getStatusVoter())) {
//             return false;
//         }
        
//         Election election = electionRepository.findById(electionId)
//             .orElseThrow(() -> new ResourceNotFoundException("Election not found"));

//         Timestamp now = Timestamp.valueOf(LocalDateTime.now());
//         if (now.before(election.getElectionStartDate()) || now.after(election.getElectionEndDate())) {
//             return false;
//         }

//         return !voteRepository.existsByVoterAndElection(voter, election);
//     }


// }