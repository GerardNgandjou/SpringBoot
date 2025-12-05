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
@Table(name = "order_items")
data class OrderItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var quantity: Int = 0,
    var price: Double = 0.0,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product = Product(),

    @ManyToOne
    @JoinColumn(name = "order_id")
    var order: Order = Order()

) {
    constructor() : this(
        id = 0,
        quantity = 0,
        price = 0.0,
        product = Product(),
        order = Order()
    )
}