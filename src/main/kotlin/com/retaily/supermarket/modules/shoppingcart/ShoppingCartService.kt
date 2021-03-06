package com.retaily.supermarket.modules.shoppingcart

import com.retaily.supermarket.database.entities.ShoppingCartEntity
import com.retaily.supermarket.database.entities.ShoppingCartRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShoppingCartService(@Autowired val shoppingCartRepository: ShoppingCartRepository) {
    fun getShoppingCartGuaranteed(userId: Long): ShoppingCartEntity {
        val shoppingCart = shoppingCartRepository.findByUserId(userId)
        return if (shoppingCart != null) shoppingCart
        else {
            val insertShoppingCart = ShoppingCartEntity(userId)
            shoppingCartRepository.save(insertShoppingCart)
        }
    }
}
