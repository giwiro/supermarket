package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.ShoppingCart

import org.hibernate.annotations.Where
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query
import javax.persistence.*


@Entity
@Cacheable(false)
@Table(name = "shopping_cart", indexes = [
    Index(name = "shopping_cart_user_id_idx", columnList = "userId", unique = true)
])
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
    var items: List<ShoppingCartItemEntity> = emptyList()

    constructor(userId: Long) : this() {
        this.userId = userId
    }

    fun mapToModel(): ShoppingCart = ShoppingCart(userId!!, items.map { it.mapToModel() })
}

interface ShoppingCartRepositoryCustom {
    fun flush()
    fun clear()
}

class ShoppingCartRepositoryImpl : ShoppingCartRepositoryCustom {
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Override
    override fun flush()  {
        entityManager.flush()
    }

    @Override
    override fun clear()  {
        entityManager.clear()
    }
}

@Repository
interface ShoppingCartRepository : CrudRepository<ShoppingCartEntity, Long>, ShoppingCartRepositoryCustom {
    @Query(value = "SELECT sc FROM #{#entityName} sc WHERE sc.userId = ?1")
    fun findByUserId(@Param("userId") userId: Long): ShoppingCartEntity?

    fun save(entity: ShoppingCartEntity): ShoppingCartEntity
}
