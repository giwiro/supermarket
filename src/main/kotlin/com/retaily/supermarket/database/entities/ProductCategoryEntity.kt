package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.ProductCategory
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Entity
@Table(name = "product_category")
class ProductCategoryEntity {
    @Id
    var categoryId: Long? = null
    var name: String? = null

    fun mapToModel(): ProductCategory = ProductCategory(categoryId!!, name!!)
}

@Repository
interface ProductCategoryRepository : CrudRepository<ProductCategoryEntity, Long>
