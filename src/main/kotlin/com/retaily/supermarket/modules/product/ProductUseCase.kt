package com.retaily.supermarket.modules.product

import com.retaily.supermarket.database.entities.ProductRepository
import com.retaily.supermarket.models.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProductUseCase(@Autowired val productRepository: ProductRepository) {
    fun getProducts(request: GetProductsRequest): List<Product> {
        val products = productRepository.findAllEnabled(request.category, request.price)
        return products.map { it.mapToModel() }
    }
}
