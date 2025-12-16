package com.relirou.login.controller

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import com.relirou.login.model.Users
import com.relirou.login.service.UserService

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun users(
        @RequestBody user: Users
    ) : Users {
        return userService.register(user)
    } 
    
}