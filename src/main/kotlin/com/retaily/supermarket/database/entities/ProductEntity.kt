package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.Product
import java.math.BigDecimal
import java.util.ArrayList
import java.util.Optional
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.PersistenceContext
import javax.persistence.Table
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

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
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_category_id", nullable = false)
    var productCategory: ProductCategoryEntity? = null

    fun mapToModel(): Product = Product(productId!!, name!!, productCategory!!.mapToModel(), price!!, imageUrl!!, enabled!!)
}

interface ProductRepositoryCustom {
    fun findAllEnabled(category: Optional<Int>, price: Optional<Int>): List<ProductEntity>
}

interface ProductRepository : CrudRepository<ProductEntity, Long>, ProductRepositoryCustom

@Repository
class ProductRepositoryImpl : ProductRepositoryCustom {
    @PersistenceContext
    var em: EntityManager? = null

    override fun findAllEnabled(category: Optional<Int>, price: Optional<Int>): List<ProductEntity> {
        val cb = em!!.criteriaBuilder
        val cq: CriteriaQuery<ProductEntity> = cb.createQuery(ProductEntity::class.java)

        val root = cq.from(ProductEntity::class.java)
        val predicates: MutableList<Predicate> = ArrayList<Predicate>()

        predicates.add(cb.equal(root.get<ProductEntity>("enabled"), true))

        if (price.isPresent) predicates.add(cb.greaterThanOrEqualTo(root.get("price"), price.get()))
        if (category.isPresent) predicates.add(cb.equal(root.get<ProductEntity>("productCategory"), category.get()))

        cq.where(*predicates.toTypedArray())

        return em!!.createQuery(cq).resultList
    }
}
