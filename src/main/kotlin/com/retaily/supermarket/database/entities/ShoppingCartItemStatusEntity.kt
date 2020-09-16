package com.retaily.supermarket.database.entities

import java.util.Optional
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Entity
@Table(name = "shopping_cart_item_status")
class ShoppingCartItemStatusEntity {
    @Id
    var shoppingCartItemStatusId: Long? = null
    var shoppingCartItemStatusName: String? = null

    @Override
    override fun toString(): String {
        return this.shoppingCartItemStatusName!!
    }
}

@Repository
interface ShoppingCartItemStatusRepository : CrudRepository<ShoppingCartItemStatusEntity, Long> {
    override fun findById(id: Long): Optional<ShoppingCartItemStatusEntity>
}
