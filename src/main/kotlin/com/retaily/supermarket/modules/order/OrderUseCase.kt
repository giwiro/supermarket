package com.retaily.supermarket.modules.order

import com.retaily.common.web.SessionService
import com.retaily.supermarket.database.entities.AddressEntity
import com.retaily.supermarket.database.entities.AddressRepository
import com.retaily.supermarket.database.entities.OrderEntity
import com.retaily.supermarket.database.entities.OrderItemEntity
import com.retaily.supermarket.database.entities.OrderItemRepository
import com.retaily.supermarket.database.entities.OrderRepository
import com.retaily.supermarket.database.entities.OrderStatusRepository
import com.retaily.supermarket.models.Order
import com.retaily.supermarket.models.OrderItemShort
import com.retaily.supermarket.models.OrderShort
import com.retaily.supermarket.models.OrderStatus
import com.retaily.supermarket.modules.pricing.PricingService
import com.retaily.supermarket.modules.shoppingcart.ShoppingCartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrderUseCase(
    @Autowired val orderRepository: OrderRepository,
    @Autowired val orderItemRepository: OrderItemRepository,
    @Autowired val addressRepository: AddressRepository,
    @Autowired val orderStatusRepository: OrderStatusRepository,
    @Autowired val pricingService: PricingService,
    @Autowired val stripeService: StripeService,
    @Autowired val sessionService: SessionService,
    @Autowired val shoppingCartService: ShoppingCartService
) {
    fun getOrders(request: GetOrdersRequest): List<Order> {
        val orders = orderRepository.findByUserId(request.userId)
        return orders.map { it.mapToModel() }
    }

    fun getOrder(request: GetOrderRequest): Order {
        val order = orderRepository.findById(request.orderId)
        if (!order.isPresent) {
            throw OrderNotFound("The order was not found")
        }
        return order.get().mapToModel()
    }

    fun createOrder(request: CreateOrdersRequest): Order {
        val shoppingCart = shoppingCartService.getShoppingCartGuaranteed(request.userId)
        val pricing = pricingService.calculate(shoppingCart.mapToModel())

        val orderStatus = orderStatusRepository.findById(OrderStatus.CREATED.id).orElseGet(null)

        val shippingAddress = addressRepository.save(
            AddressEntity(
                request.shippingAddress.firstName,
                request.shippingAddress.lastName,
                request.shippingAddress.address1,
                request.shippingAddress.address2,
                request.shippingAddress.city,
                request.shippingAddress.state,
                request.shippingAddress.zip
            )
        )

        val billingAddress = addressRepository.save(
            AddressEntity(
                request.billingAddress.firstName,
                request.billingAddress.lastName,
                request.billingAddress.address1,
                request.billingAddress.address2,
                request.billingAddress.city,
                request.billingAddress.state,
                request.billingAddress.zip
            )
        )

        val insertedOrder = orderRepository.save(
            OrderEntity(request.userId, orderStatus, shippingAddress, billingAddress)
        )

        shoppingCart.items.forEach {
            orderItemRepository.save(
                OrderItemEntity(
                    insertedOrder,
                    it.product!!.name!!,
                    it.product!!.price!!,
                    it.product!!.imageUrl!!,
                    it.amount!!,
                    it.product!!.category!!
                )
            )
        }

        orderRepository.save(insertedOrder)

        orderRepository.flush()
        orderRepository.clear()

        val order = getOrder(GetOrderRequest(insertedOrder.orderId!!))

        val orderShort = OrderShort(
            order.id,
            order.status,
            order.items.map { i -> OrderItemShort(
                i.id,
                i.price,
                i.amount
            ) }
        )

        val paymentIntent =
            stripeService.createPaymentIntent(
                pricing,
                orderShort,
                billingAddress.mapToModel(),
                sessionService.getUserId()!!
            )

        insertedOrder.paymentToken = paymentIntent.clientSecret

        orderRepository.save(insertedOrder)

        orderRepository.flush()
        orderRepository.clear()

        return getOrder(GetOrderRequest(insertedOrder.orderId!!))
    }
}
