package com.retaily.supermarket.database.entities

import com.retaily.supermarket.models.ShoppingCartItem
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.*


@Entity
@Cacheable(false)
@Table(name = "shopping_cart_item")
class ShoppingCartItemEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var shoppingCartItemId: Long? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopping_cart_item_status_id", nullable = false)
    var shoppingCartItemStatus: ShoppingCartItemStatusEntity? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    var shoppingCart: ShoppingCartEntity? = null

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_item_product_id", nullable = false)
    var product: ProductEntity? = null
    var amount: Int? = null

    constructor(shoppingCart: ShoppingCartEntity,
                shoppingCartItemStatus: ShoppingCartItemStatusEntity,
                product: ProductEntity,
                amount: Int) : this() {
        this.shoppingCart = shoppingCart
        this.shoppingCartItemStatus = shoppingCartItemStatus
        this.product = product
        this.amount = amount
    }

    fun mapToModel(): ShoppingCartItem =
            ShoppingCartItem(
                    shoppingCartItemId!!,
                    shoppingCartItemStatus.toString(),
                    product!!.mapToModel(),
                    amount!!)
}

@Repository
interface ShoppingCartItemRepository : CrudRepository<ShoppingCartItemEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT sci.* FROM supermarket.shopping_cart_item sci " +
            "WHERE sci.shopping_cart_id = ?1 AND sci.shopping_cart_item_product_id = ?2 AND shopping_cart_item_status_id = 1")
    fun findByShoppingCartAndProduct(@Param("shoppingCartId") shoppingCartId: Long,
                                     @Param("shoppingCartId") productId: Long): ShoppingCartItemEntity?

    @Modifying(clearAutomatically = true)
    fun save(entity: ShoppingCartItemEntity): ShoppingCartItemEntity

    override fun delete(entity: ShoppingCartItemEntity)
}