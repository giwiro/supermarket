package com.retaily.supermarket.models

enum class OrderStatus(val id: Long) {
    CREATED(1),
    PAID(2),
    ARCHIVED(10)
}
