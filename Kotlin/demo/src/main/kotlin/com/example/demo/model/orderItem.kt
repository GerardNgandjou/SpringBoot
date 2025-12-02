package com.example.demo.model

import jakarta.persistence.*
import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import lombok.Setter
import lombok.Getter

/**
 * Represents an item in an order (links product with order and quantity).
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
data class OrderItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var quantity: Int,
    var price: Double, // price at the time of order

    // Many order items belong to one order
    // @ManyToOne
    // @JoinColumn(name = "order_id")
    // val order: Order,

    // // Many order items refer to one product
    // @ManyToOne
    // @JoinColumn(name = "product_id")
    // val product: Product
)