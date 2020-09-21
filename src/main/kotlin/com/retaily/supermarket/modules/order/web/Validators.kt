package com.retaily.supermarket.modules.order.web

import javax.validation.Valid
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class AddressValidator {
    @NotNull(message = "First name cannot be missing or empty")
    @Size(min = 2, message = "First name must not be less than 2 characters")
    val firstName: String? = null

    @NotNull(message = "Last name cannot be missing or empty")
    @Size(min = 2, message = "Last name must not be less than 2 characters")
    val lastName: String? = null

    @NotNull(message = "Address1 cannot be missing or empty")
    @Size(min = 5, message = "Address1 must not be less than 5 characters")
    val address1: String? = null

    // @Size(min = 5, message = "Address2 must not be less than 5 characters")
    val address2: String? = null

    @NotNull(message = "City cannot be missing or empty")
    @Size(min = 2, message = "City must not be less than 2 characters")
    val city: String? = null

    @NotNull(message = "State cannot be missing or empty")
    @Size(min = 2, message = "State must not be less than 2 characters")
    val state: String? = null

    @NotNull(message = "Zip cannot be missing or empty")
    @Size(min = 2, message = "Zip must not be less than 2 characters")
    val zip: String? = null
}

class CreateOrderRequestValidator {
    @Valid
    val shippingAddress: AddressValidator? = null

    @Valid
    val billingAddress: AddressValidator? = null
}
