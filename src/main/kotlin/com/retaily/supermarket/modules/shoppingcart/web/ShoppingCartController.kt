package com.retaily.supermarket.modules.shoppingcart.web

import com.retaily.supermarket.common.web.Authorized
import com.retaily.supermarket.common.web.SessionService
import com.retaily.supermarket.models.ShoppingCart
import com.retaily.supermarket.modules.shoppingcart.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/supermarket/shopping-cart"])
class ShoppingCartController constructor(@Autowired val useCase: ShoppingCartUseCase,
                                         @Autowired val sessionService: SessionService) {

    @GetMapping(path = ["/"])
    @Authorized
    fun getShoppingCart(): ShoppingCart {
        val userId = sessionService.getUserId()
        return useCase.getShoppingCart(GetShoppingCartRequest(userId!!))
    }

    @PostMapping(path = ["/{productId}"])
    @Authorized
    fun addProduct(@PathVariable productId: Long,
                   @Valid @RequestBody requestBody: AddProductRequestValidator): ShoppingCart {
        val userId = sessionService.getUserId()
        val request = AddProductRequest(userId!!, productId, requestBody.amount!!)
        useCase.addProduct(request)
        return useCase.getShoppingCart(GetShoppingCartRequest(userId))
    }

    @PutMapping(path = ["/{productId}"])
    @Authorized
    fun updateProduct(@PathVariable productId: Long,
                      @Valid @RequestBody requestBody: UpdateProductRequestValidator): ShoppingCart {
        val userId = sessionService.getUserId()
        val request = UpdateProductRequest(userId!!, productId, requestBody.amount!!)
        useCase.updateProduct(request)
        return useCase.getShoppingCart(GetShoppingCartRequest(userId))
    }

    @DeleteMapping(path = ["/{productId}"])
    @Authorized
    fun deleteProduct(@PathVariable productId: Long) {
        val userId = sessionService.getUserId()
        val request = DeleteProductRequest(userId!!, productId)
        useCase.deleteProduct(request)
    }
}