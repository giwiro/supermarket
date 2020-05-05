package com.retaily.supermarket.modules.shoppingcart.web

import javax.validation.constraints.NotNull


class AddProductRequestValidator {
    @NotNull(message = "Amount is required")
    val amount: Int? = null
}


class UpdateProductRequestValidator {
    @NotNull(message = "Amount is required")
    val amount: Int? = null
}