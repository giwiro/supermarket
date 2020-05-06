package com.retaily.supermarket.models;

data class ShoppingCart(val id: Long, val items: List<ShoppingCartItem>) {
    val type = "shopping-cart"
}