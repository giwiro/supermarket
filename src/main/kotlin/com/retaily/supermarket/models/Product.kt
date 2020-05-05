package com.retaily.supermarket.models

import java.math.BigDecimal

data class Product(val productId: Long, val name: String, val price: BigDecimal, val imageUrl: String, val enabled: Boolean)