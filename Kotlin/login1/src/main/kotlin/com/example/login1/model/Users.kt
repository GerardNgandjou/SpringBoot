package com.example.login1.model

import jakarta.persistence.*

@Entity
data class Users(

    @Id
    val id: Long,
    val name: String,
    var password: String
)