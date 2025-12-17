package com.example.login1.repository

import com.example.login1.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<Users, Long> {

    fun findByName(username: String): Users?

}