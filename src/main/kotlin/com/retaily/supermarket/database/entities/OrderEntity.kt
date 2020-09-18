package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.Order
import java.util.Date
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.PersistenceContext
import javax.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Entity
@Table(
    name = "order",
    indexes = [
        Index(name = "order_user_id_order_id_idx", columnList = "userId,orderId", unique = true)
    ]
)
class OrderEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var orderId: Long? = null
    var userId: Long? = null

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status_id", nullable = false)
    var orderStatus: OrderStatusEntity? = null

    @OneToMany(
        mappedBy = "order",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    var items: List<OrderItemEntity> = emptyList()

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_address_id", nullable = false)
    var shippingAddress: AddressEntity? = null

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "billing_address_id", nullable = false)
    var billingAddress: AddressEntity? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Date? = null

    constructor(userId: Long, shippingAddress: AddressEntity, billingAddress: AddressEntity) : this() {
        this.userId = userId
        this.shippingAddress = shippingAddress
        this.billingAddress = billingAddress
    }

    fun mapToModel(): Order = Order(userId!!, orderStatus.toString())
}

interface OrderRepositoryCustom {
    fun flush()
    fun clear()
}

class OrderRepositoryImpl : OrderRepositoryCustom {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Override
    override fun flush() {
        entityManager.flush()
    }

    @Override
    override fun clear() {
        entityManager.clear()
    }
}

@Repository
interface OrderRepository : CrudRepository<OrderEntity, Long>, OrderRepositoryCustom {
    @Query(value = "SELECT sc FROM #{#entityName} sc WHERE sc.userId = ?1")
    fun findByUserId(@Param("userId") userId: Long): List<OrderEntity>

    fun save(entity: OrderEntity): OrderEntity
}
