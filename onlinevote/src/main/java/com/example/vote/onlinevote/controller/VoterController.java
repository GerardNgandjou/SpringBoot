package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.dto.VoterDto;
import com.example.vote.onlinevote.dto.VoterResponseDto;
import com.example.vote.onlinevote.sevirce.VoterService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Getter
@Controller
@RestController
@RequestMapping
public class VoterController {

    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping("/voter/add")
    public VoterResponseDto register(
            @Valid @RequestBody VoterDto voterDto
    ) {
        return voterService.saveVoter(voterDto);
    }


//    @PostMapping("/voter/register")
//    public String processRegistration(@Valid @ModelAttribute("voter") Voter voter,
//                                      BindingResult result,
//                                      Model model) {
//        if (result.hasErrors()) {
//            return "register";
//        }
//
//        // Save voter to database
//        voterService.save(voter);
//
//        return "redirect:/success";
//    }

    @GetMapping("/voter/display")
    public List<VoterResponseDto> show() {
        return voterService.showAllVoters();
    }

    @GetMapping("/voter/find/{id}")  // Get User with her id
    public VoterResponseDto getVoterById(
            @PathVariable Long id
    ){
        return voterService.findVoterById(id);
    }

    @GetMapping("/voter/search/{voter_name}")  // et User with her name
    public List<VoterResponseDto> findRegistrationsByFirstName(
            @PathVariable("voter_name") String firstName
    ){
        return voterService.findVoterByFirstName(firstName);
    }

    @DeleteMapping("/voter/delete/{vote-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteVoterById(
            @PathVariable("vote-id") Long id
    ) {
        voterService.deleteRegistrationById(id);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidationException(
            MethodArgumentNotValidException exp
    ) {
        var errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName = ((FieldError) error).getField();
                    var message = error.getDefaultMessage();
                    errors.put(fieldName, message);
                });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
