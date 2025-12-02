package com.example.demo.model

import java.time.LocalDateTime
import jakarta.persistence.*
import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import lombok.Setter
import lombok.Getter


/**
 * Represents an order placed by a user.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var orderDate: LocalDateTime = LocalDateTime.now(),
    var status: String, // could be "pending", "shipped", "delivered"
    var totalAmount: Double = 0.0,

    // Many orders belong to one user
    // @ManyToOne
    // @JoinColumn(name = "user_id")
    // val user: User,

    // // One order can have many order items
    // @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL])
    // val orderItems: List<OrderItem> = listOf()
)