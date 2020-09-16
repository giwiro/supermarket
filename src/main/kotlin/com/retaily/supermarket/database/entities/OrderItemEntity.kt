package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.OrderItem
import org.hibernate.annotations.CreationTimestamp
import javax.persistence.Cacheable
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.Date
import javax.persistence.Column

@Entity
@Cacheable(false)
@Table(name = "order_item")
class OrderItemEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderItemId: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: OrderEntity? = null

    var name: String? = null
    var price: BigDecimal? = null
    var imageUrl: String? = null
    var amount: Int? = null

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    var category: ProductCategoryEntity? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Date? = null

    constructor(
        order: OrderEntity,
        name: String,
        price: BigDecimal,
        imageUrl: String,
        amount: Int,
        category: ProductCategoryEntity
    ) : this() {
        this.order = order
        this.name = name
        this.price = price
        this.imageUrl = imageUrl
        this.amount = amount
        this.category = category
    }

    fun mapToModel(): OrderItem =
        OrderItem(
            orderItemId!!,
            name!!,
            category!!.mapToModel(),
            price!!,
            imageUrl!!,
            amount!!
        )
}

@Repository
interface OrderItemRepository : CrudRepository<OrderItemEntity, Long> {
    fun save(entity: OrderItemEntity): OrderItemEntity
}
