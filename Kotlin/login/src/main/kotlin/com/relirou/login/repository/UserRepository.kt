package com.relirou.login.repository

import com.relirou.login.model.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<Users, Long> {

    fun findByName(username: String): Users?

}