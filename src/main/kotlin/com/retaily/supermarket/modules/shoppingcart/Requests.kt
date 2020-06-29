package com.retaily.supermarket.modules.shoppingcart

data class GetShoppingCartRequest(val userId: Long)

data class AddProductRequest(val userId: Long, val productId: Long, val amount: Int)

data class UpdateProductRequest(val userId: Long, val productId: Long, val amount: Int)

data class DeleteProductRequest(val userId: Long, val productId: Long)
