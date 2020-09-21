package com.retaily.supermarket.modules.order.web

import com.retaily.common.web.SessionService
import com.retaily.supermarket.models.Address
import com.retaily.supermarket.models.Order
import com.retaily.supermarket.modules.order.CreateOrdersRequest
import com.retaily.supermarket.modules.order.GetOrderRequest
import com.retaily.supermarket.modules.order.OrderUseCase
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/supermarket/order"])
class OrderController constructor(
    @Autowired val useCase: OrderUseCase,
    @Autowired val sessionService: SessionService
) {
    @PostMapping(path = ["/"])
    fun createOrder(@Valid @RequestBody requestBody: CreateOrderRequestValidator): Order {
        val shippingAddress = Address(
            requestBody.shippingAddress!!.firstName!!,
            requestBody.shippingAddress.lastName!!,
            requestBody.shippingAddress.address1!!,
            requestBody.shippingAddress.address2!!,
            requestBody.shippingAddress.city!!,
            requestBody.shippingAddress.state!!,
            requestBody.shippingAddress.zip!!
        )

        val billingAddress = Address(
            requestBody.billingAddress!!.firstName!!,
            requestBody.billingAddress.lastName!!,
            requestBody.billingAddress.address1!!,
            requestBody.billingAddress.address2!!,
            requestBody.billingAddress.city!!,
            requestBody.billingAddress.state!!,
            requestBody.billingAddress.zip!!
        )

        val request = CreateOrdersRequest(
            sessionService.getUserId()!!,
            shippingAddress,
            billingAddress
        )

        return useCase.createOrder(request)
    }
}
