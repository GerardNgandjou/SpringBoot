// package com.example.vote.onlinevote.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.vote.onlinevote.dto.VoteRequestDTO;
// import com.example.vote.onlinevote.dto.VoteResponseDTO;
// import com.example.vote.onlinevote.exception.DuplicateVoteException;
// import com.example.vote.onlinevote.exception.ElectionEndedException;
// import com.example.vote.onlinevote.exception.ElectionNotStartedException;
// import com.example.vote.onlinevote.exception.ForbiddenException;
// import com.example.vote.onlinevote.exception.InvalidCandidateException;
// import com.example.vote.onlinevote.exception.ResourceNotFoundException;
// import com.example.vote.onlinevote.exception.UnauthorizedException;
// import com.example.vote.onlinevote.sevirce.VoteService;


// @RestController
// @RequestMapping("/api/votes")
// public class VoteController {
    
//     private final VoteService voteService;

//     @Autowired
//     public VoteController(VoteService voteService) {
//         this.voteService = voteService;
//     }

//     @PostMapping
//     public ResponseEntity<?> castVote(@RequestBody VoteRequestDTO voteRequest,
//                                     @AuthenticationPrincipal UserDetails userDetails) {
//         try {
//             VoteResponseDTO response = voteService.castVote(voteRequest, userDetails.getUsername());
//             return ResponseEntity.ok(response);
//         } catch (UnauthorizedException e) {
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//         } catch (ForbiddenException e) {
//             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
//         } catch (ResourceNotFoundException e) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//         } catch (DuplicateVoteException | ElectionNotStartedException | ElectionEndedException | InvalidCandidateException e) {
//             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//         }
//     }
// }