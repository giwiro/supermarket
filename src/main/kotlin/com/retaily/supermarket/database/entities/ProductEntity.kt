package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.Product
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "product")
class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var productId: Long? = null
    var name: String? = null
    var price: BigDecimal? = null
    var imageUrl: String? = null
    var enabled: Boolean? = null

    fun mapToModel(): Product = Product(productId!!, name!!, price!!, imageUrl!!, enabled!!)
}

@Repository
interface ProductRepository : CrudRepository<ProductEntity, Long> {
    @Query(value = "SELECT p FROM #{#entityName} p WHERE p.enabled = true")
    fun findAllEnabled(): List<ProductEntity>?

    override fun findById(id: Long): Optional<ProductEntity>
}