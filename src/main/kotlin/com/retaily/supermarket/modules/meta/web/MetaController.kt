package com.retaily.supermarket.modules.meta.web

import com.retaily.supermarket.models.ProductCategory
import com.retaily.supermarket.modules.meta.MetaUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/supermarket/meta"])
class MetaController constructor(@Autowired val useCase: MetaUseCase) {

    @GetMapping(path = ["/product-category"])
    fun getProductCategories(): List<ProductCategory> {
        return useCase.getProductCategories()
    }
}
