package com.retaily.supermarket.modules.shoppingcart.web

import com.retaily.common.web.Authorized
import com.retaily.common.web.SessionService
import com.retaily.supermarket.models.ShoppingCart
import com.retaily.supermarket.modules.shoppingcart.AddProductRequest
import com.retaily.supermarket.modules.shoppingcart.DeleteProductRequest
import com.retaily.supermarket.modules.shoppingcart.GetShoppingCartRequest
import com.retaily.supermarket.modules.shoppingcart.ShoppingCartUseCase
import com.retaily.supermarket.modules.shoppingcart.UpdateProductRequest
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/supermarket/shopping-cart"])
class ShoppingCartController constructor(
    @Autowired val useCase: ShoppingCartUseCase,
    @Autowired val sessionService: SessionService
) {

    @GetMapping(path = ["/"])
    @Authorized
    fun getShoppingCart(): ShoppingCart {
        val userId = sessionService.getUserId()
        return useCase.getShoppingCart(GetShoppingCartRequest(userId!!))
    }

    @PostMapping(path = ["/{productId}"])
    @Authorized
    fun addProduct(
        @PathVariable productId: Long,
        @Valid @RequestBody requestBody: AddProductRequestValidator
    ): ShoppingCart {
        val userId = sessionService.getUserId()
        val request = AddProductRequest(userId!!, productId, requestBody.amount!!)
        useCase.addProduct(request)
        return useCase.getShoppingCart(GetShoppingCartRequest(userId))
    }

    @PutMapping(path = ["/{productId}"])
    @Authorized
    fun updateProduct(
        @PathVariable productId: Long,
        @Valid @RequestBody requestBody: UpdateProductRequestValidator
    ): ShoppingCart {
        val userId = sessionService.getUserId()
        val request = UpdateProductRequest(userId!!, productId, requestBody.amount!!)
        useCase.updateProduct(request)
        return useCase.getShoppingCart(GetShoppingCartRequest(userId))
    }

    @DeleteMapping(path = ["/{productId}"])
    @Authorized
    fun deleteProduct(@PathVariable productId: Long): ShoppingCart {
        val userId = sessionService.getUserId()
        val request = DeleteProductRequest(userId!!, productId)
        useCase.deleteProduct(request)
        return useCase.getShoppingCart(GetShoppingCartRequest(userId))
    }
}
