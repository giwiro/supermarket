package com.retaily.supermarket.models

data class ShoppingCartItem(val id: Long,
                            val shoppingCartItemStatus: String,
                            val product: Product,
                            val amount: Int) {
    val type = "shopping-cart-item"
}