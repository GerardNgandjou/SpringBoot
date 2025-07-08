package com.example.vote.onlinevote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.vote.onlinevote.model.Login;
import com.example.vote.onlinevote.repository.LoginRepository;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Login saveLogin(@RequestBody Login login) {
        return loginRepository.save(login);
    }

}
