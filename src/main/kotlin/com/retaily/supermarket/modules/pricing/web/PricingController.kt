package com.retaily.supermarket.modules.pricing.web

import com.retaily.common.web.SessionService
import com.retaily.supermarket.models.Pricing
import com.retaily.supermarket.modules.pricing.CalculateRequest
import com.retaily.supermarket.modules.pricing.PricingUseCase
import com.retaily.supermarket.modules.shoppingcart.ShoppingCartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/supermarket/pricing"])
class PricingController constructor(
    @Autowired val useCase: PricingUseCase,
    @Autowired val sessionService: SessionService,
    @Autowired val shoppingCartService: ShoppingCartService
) {
    @GetMapping(path = ["/calculate"])
    fun calculate(): Pricing {
        val userId = sessionService.getUserId()
        val shoppingCart = shoppingCartService.getShoppingCartGuaranteed(userId!!).mapToModel()
        return useCase.calculate(CalculateRequest(shoppingCart))
    }
}
