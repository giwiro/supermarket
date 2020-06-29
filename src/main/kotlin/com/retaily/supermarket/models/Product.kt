package com.retaily.supermarket.models

import java.math.BigDecimal

data class Product(val id: Long, val name: String, val category: ProductCategory, val price: BigDecimal, val imageUrl: String, val enabled: Boolean) {
    val type = "product"
}
