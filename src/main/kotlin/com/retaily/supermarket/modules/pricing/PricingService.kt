package com.retaily.supermarket.modules.pricing

import com.retaily.supermarket.models.Pricing
import com.retaily.supermarket.models.ShoppingCart
import java.math.BigDecimal
import org.springframework.stereotype.Service

@Service
class PricingService {
    fun calculate(shoppingCart: ShoppingCart): Pricing {
        var subtotal = BigDecimal.ZERO

        // Calculate subtotal
        for (i in shoppingCart.items) {
            val a = BigDecimal(i.amount)
            subtotal += i.product.price.multiply(a)
        }

        subtotal = subtotal.setScale(2, BigDecimal.ROUND_HALF_UP)

        // Calculate taxes
        val taxes = subtotal
            .times(BigDecimal(0.025))
            .setScale(2, BigDecimal.ROUND_HALF_UP)

        // Calculate payment fee
        val paymentFeeDeductible = subtotal.add(taxes)
        val paymentFee = paymentFeeDeductible
            .times(BigDecimal(0.04))
            .setScale(2, BigDecimal.ROUND_HALF_UP)

        // Business commission
        val commissionDeductible = subtotal.add(taxes).add(paymentFee)
        val commission = commissionDeductible
            .times(BigDecimal(0.06))
            .setScale(2, BigDecimal.ROUND_HALF_UP)

        // Total
        val total = subtotal
            .add(taxes)
            .add(paymentFee)
            .add(commission)
            .setScale(2, BigDecimal.ROUND_HALF_UP)

        return Pricing(subtotal, taxes, commission, paymentFee, total)
    }
}
