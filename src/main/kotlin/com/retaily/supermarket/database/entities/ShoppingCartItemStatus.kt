package com.retaily.supermarket.database.entities

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "shopping_cart_item_status")
class ShoppingCartItemStatusEntity {
    @Id
    var shoppingCartItemStatusId: Long? = null
    var shoppingCartItemStatusName: String? = null

    @Override
    override fun toString(): String {
        return this.shoppingCartItemStatusName!!;
    }
}

@Repository
interface ShoppingCartItemStatusRepository : CrudRepository<ShoppingCartItemStatusEntity, Long> {
    override fun findById(id: Long): Optional<ShoppingCartItemStatusEntity>
}