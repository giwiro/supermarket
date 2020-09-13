package com.retaily.supermarket.models

data class OrderItem(
    val id: Long,
    val product: Product,
    val amount: Int
) {
    val type = "order-item"
}
