package com.relirou.login.model

import jakarta.persistence.*

@Entity
data class Users(

    @Id
    val id: Long,
    val name: String,
    var password: String
)