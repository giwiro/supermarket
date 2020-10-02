package com.retaily.supermarket.modules.order

import com.retaily.supermarket.models.Address

data class GetOrdersRequest(val userId: Long)

data class GetOrderRequest(val orderId: Long)

data class CreateOrdersRequest(val userId: Long, val shippingAddress: Address, val billingAddress: Address)

data class ConfirmOrderPaymentRequest(val orderId: Long)
