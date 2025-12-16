package com.relirou.login.service

import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import com.relirou.login.repository.UserRepository
import com.relirou.login.model.Users

@Service
class UserService (
    private val repo: UserRepository
) {

    // fun passwordEncoder(): PasswordEncoder {
    //     return BCryptPasswordEncoder(12)
    // }
    

    // fun register(user: Users): Users {
    //     // user.password = passwordEncoder().encode(user.password)
    //     user.password = passwordEncoder().encode(user.password)
    //     return repo.save(user)
    // }

        private val encoder = BCryptPasswordEncoder(12)

    fun register(user: Users): Users {
        val encodedPassword = encoder.encode(user.password) ?: 
            throw IllegalStateException("Password encoding failed")
        
        val userWithEncodedPassword = user.copy(password = encodedPassword)
        return repo.save(userWithEncodedPassword)
    }

} 