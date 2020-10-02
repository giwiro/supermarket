package com.retaily.supermarket.modules.order.event

import com.retaily.common.models.Invoice
import com.retaily.supermarket.modules.order.ConfirmOrderPaymentRequest
import com.retaily.supermarket.modules.order.GetOrderRequest
import com.retaily.supermarket.modules.order.OrderUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderEventListener(@Autowired val useCase: OrderUseCase) {
    @KafkaListener(
        topics = ["\${retaily.event.checkout.invoice.create.topic}"],
        groupId = "\${spring.application.name}"
    )
    fun invoiceCreatedHandler(invoice: Invoice) {
        println("@@@@@@@@@@@@")
        println(invoice.id)
        val oldOrder = useCase.getOrder(GetOrderRequest(invoice.orderId!!))
        print("found order")
        println(oldOrder)
        useCase.confirmOrderPayment(ConfirmOrderPaymentRequest(oldOrder.id))
    }
}
