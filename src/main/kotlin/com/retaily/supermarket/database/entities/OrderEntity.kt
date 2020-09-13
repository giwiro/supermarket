package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.Order
import javax.persistence.Entity
import javax.persistence.EntityManager
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.PersistenceContext
import javax.persistence.Table
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
    /*@OneToMany(
        mappedBy = "shoppingCart",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    var items: List<ShoppingCartItemEntity> = emptyList()*/

    constructor(userId: Long) : this() {
        this.userId = userId
    }

    fun mapToModel(): Order = Order(userId!!)
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
