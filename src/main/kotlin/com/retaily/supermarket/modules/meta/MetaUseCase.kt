package com.retaily.supermarket.modules.meta

import com.retaily.supermarket.database.entities.ProductCategoryRepository
import com.retaily.supermarket.models.ProductCategory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MetaUseCase(@Autowired val productCategoryRepository: ProductCategoryRepository) {
    fun getProductCategories(): List<ProductCategory> {
        val categories = productCategoryRepository.findAll()
        return categories.map { it.mapToModel() }
    }
}
