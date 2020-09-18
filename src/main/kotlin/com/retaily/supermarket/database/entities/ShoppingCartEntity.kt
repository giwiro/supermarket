package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.ShoppingCart
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
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import javax.persistence.PersistenceContext
import javax.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Where
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Entity
@Table(
    name = "shopping_cart",
    indexes = [
        Index(name = "shopping_cart_user_id_idx", columnList = "userId", unique = true)
    ]
)
class ShoppingCartEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var shoppingCartId: Long? = null
    var userId: Long? = null
    @OneToMany(
        mappedBy = "shoppingCart",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    @Where(clause = "shopping_cart_item_status_id = 1")
    @OrderBy(value = "created_at ASC")
    var items: List<ShoppingCartItemEntity> = emptyList()

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Date? = null

    constructor(userId: Long) : this() {
        this.userId = userId
    }

    fun mapToModel(): ShoppingCart = ShoppingCart(shoppingCartId!!, items.map { it.mapToModel() })
}

interface ShoppingCartRepositoryCustom {
    fun flush()
    fun clear()
}

class ShoppingCartRepositoryImpl : ShoppingCartRepositoryCustom {
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
interface ShoppingCartRepository : CrudRepository<ShoppingCartEntity, Long>, ShoppingCartRepositoryCustom {
    @Query(value = "SELECT sc FROM #{#entityName} sc WHERE sc.userId = ?1")
    fun findByUserId(@Param("userId") userId: Long): ShoppingCartEntity?

    fun save(entity: ShoppingCartEntity): ShoppingCartEntity
}
