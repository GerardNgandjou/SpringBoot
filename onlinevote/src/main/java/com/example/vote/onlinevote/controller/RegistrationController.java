package com.example.vote.onlinevote.controller;

import com.example.vote.onlinevote.model.Registration;
import com.example.vote.onlinevote.repository.RegistrationRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
public class RegistrationController {

    private final RegistrationRepository registrationRepository;

    public RegistrationController(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @PostMapping("/Registration")  //Add a new Registration
    public Registration saveRegistration(
            @RequestBody Registration Registration
    ){
        return registrationRepository.save(Registration);
    }

    @GetMapping("/Registration")  // Get all the Registration in the database
    public List<Registration> getAllRegistrations(){
        return registrationRepository.findAll();
    }

    @GetMapping("/Registration/{id}")  // Get Registration with her id
    public Registration getRegistrationById(
            @PathVariable Long id
    ){
        return registrationRepository.findById(id)
                .orElse(new Registration());
    }

    @GetMapping("/Registration/search/{Registration_name}")  // et Registration with her name
    public List<Registration> findRegistrationsByFirstName(
            @PathVariable("Registration_name") String firstName
    ){
        return registrationRepository.findAllByFirstnameContaining(firstName);
    }

    @DeleteMapping("/delete/{regis-id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteRegistrationById(
            @PathVariable("regis-id") Long id
    ) {
        registrationRepository.deleteById(id);
    }

}
