package com.retaily.supermarket.modules.order

import com.retaily.supermarket.database.entities.OrderRepository
import com.retaily.supermarket.models.Order
import com.retaily.supermarket.modules.shoppingcart.ShoppingCartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderUseCase(
    @Autowired val orderRepository: OrderRepository,
    @Autowired val shoppingCartService: ShoppingCartService
) {
    fun getOrders(request: GetOrdersRequest): List<Order> {
        val orders = orderRepository.findByUserId(request.userId)
        return orders.map{ it.mapToModel() }
    }

    /*fun createOrder(request: CreateOrdersRequest): Order {
        val shoppingCart = shoppingCartService.getShoppingCartGuaranteed(request.userId)
        shoppingCart.items
    }*/
}
