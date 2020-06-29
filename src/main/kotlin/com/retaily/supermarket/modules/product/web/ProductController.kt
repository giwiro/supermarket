package com.retaily.supermarket.modules.product.web

import com.retaily.supermarket.models.Product
import com.retaily.supermarket.modules.product.GetProductsRequest
import com.retaily.supermarket.modules.product.ProductUseCase
import java.util.Optional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/supermarket/product"])
class ProductController constructor(@Autowired val useCase: ProductUseCase) {
    @GetMapping(path = ["/list"])
    fun getProducts(@RequestParam category: Optional<Int>, @RequestParam price: Optional<Int>): List<Product> =
        useCase.getProducts(GetProductsRequest(category, price))
}
