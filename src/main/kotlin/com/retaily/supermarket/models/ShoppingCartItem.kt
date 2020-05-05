package com.retaily.supermarket.models

data class ShoppingCartItem(val shoppingCartItemId: Long,
                            val shoppingCartItemStatus: String,
                            val product: Product,
                            val amount: Int)