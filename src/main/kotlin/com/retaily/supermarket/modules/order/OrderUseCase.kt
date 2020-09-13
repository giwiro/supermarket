package com.retaily.supermarket.modules.order

import com.retaily.supermarket.database.entities.OrderRepository
import com.retaily.supermarket.models.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderUseCase(@Autowired val orderRepository: OrderRepository) {
    fun getOrders(request: GetOrdersRequest): List<Order> {
        val orders = orderRepository.findByUserId(request.userId)
        return orders.map{ it.mapToModel() }
    }
}
