package com.retaily.supermarket.modules.order

import com.google.gson.Gson
import com.retaily.supermarket.models.Address
import com.retaily.supermarket.models.OrderShort
import com.retaily.supermarket.models.Pricing
import com.stripe.Stripe
import com.stripe.model.PaymentIntent
import java.math.BigDecimal
import java.util.ArrayList
import java.util.HashMap
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class StripeService {
    val logger = LoggerFactory.getLogger(StripeService::class.java)

    @Value("\${stripe.keys.secret}")
    val apiSecret: String? = null

    fun createPaymentIntent(
        pricing: Pricing,
        order: OrderShort,
        billingAddress: Address,
        userId: Long
    ): PaymentIntent {
        Stripe.apiKey = apiSecret
        val paymentMethodTypes: MutableList<Any> = ArrayList()
        paymentMethodTypes.add("card")

        val params: MutableMap<String, Any> = HashMap()
        params["setup_future_usage"] = "off_session"
        params["amount"] = pricing.total.multiply(BigDecimal("100.00")).toInt()
        params["currency"] = "usd"
        params["payment_method_types"] = paymentMethodTypes

        val gson = Gson()

        val pricingJSON = gson.toJson(pricing)
        val orderJSON = gson.toJson(order)
        val billingAddressJSON = gson.toJson(billingAddress)

        params["metadata"] = mapOf(
            "pricing" to pricingJSON,
            "order" to orderJSON,
            "billing_address" to billingAddressJSON,
            "user_id" to userId
        )

        val i = PaymentIntent.create(params)
        logger.info("PaymentIntent created: ${i.id}")
        return i
    }
}
