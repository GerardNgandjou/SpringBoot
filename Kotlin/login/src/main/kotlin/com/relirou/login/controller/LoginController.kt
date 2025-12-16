package com.relirou.login.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/api")
class LoginController {

    @GetMapping("/login")
    fun login(): String {
        return "Login successful!"
    }
 
}