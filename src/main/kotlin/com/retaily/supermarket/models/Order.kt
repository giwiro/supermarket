package com.retaily.supermarket.models

data class Order(val id: Long, val status: String) { // , val items: List<OrderItem>) {
    val type = "order"
}
