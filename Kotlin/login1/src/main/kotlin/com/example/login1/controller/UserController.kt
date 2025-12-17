package com.example.login1.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import com.example.login1.service.UserService
import com.example.login1.model.Users

@RestController
@RequestMapping("/auth")
class UserController(
    private val userService: UserService 
) {

    @PostMapping("/register")
    fun register(
        @RequestBody user: Users
    ) : Users {
        return userService.register(user)
    }

    @PostMapping("/login")
    fun login (@RequestBody users : Users) : String {
        return userService.verify(users)
    }
    
}

