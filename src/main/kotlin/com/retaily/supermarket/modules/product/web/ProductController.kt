package com.retaily.supermarket.modules.product.web

import com.retaily.supermarket.models.Product
import com.retaily.supermarket.modules.product.ProductUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpSession

@RestController
@RequestMapping(path = ["/supermarket/product"])
class ProductController constructor(@Autowired val useCase: ProductUseCase) {
    @GetMapping(path = ["/list"])
    fun getProducts() = useCase.getProducts()
}