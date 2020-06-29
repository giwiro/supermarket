package com.retaily.supermarket.modules.product

import java.util.Optional

data class GetProductsRequest(val category: Optional<Int>, val price: Optional<Int>)
