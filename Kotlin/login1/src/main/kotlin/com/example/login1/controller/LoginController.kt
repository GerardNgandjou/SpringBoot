package com.example.login1.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api")
class LoginController {

    @GetMapping("/logi")
    fun login(): String {
        return "Login successful!"
    }
 
}