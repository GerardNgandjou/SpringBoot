package com.example.demo.model

import java.time.LocalDateTime
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

/**
 * Represents a user/customer of the online store.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String,
    var email: String,
    var password: String,
    var address: String,
    var phoneNumber: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),

    // One user can have many orders
    // @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    // val orders: List<Order> = listOf()
)