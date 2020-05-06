package com.retaily.supermarket.modules.shoppingcart

import com.retaily.supermarket.database.entities.*
import com.retaily.supermarket.models.ShoppingCart
import com.retaily.supermarket.models.ShoppingCartItem
import com.retaily.supermarket.models.ShoppingCartItemStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

@Component
class ShoppingCartUseCase(@Autowired val shoppingCartRepository: ShoppingCartRepository,
                          @Autowired val shoppingCartItemRepository: ShoppingCartItemRepository,
                          @Autowired val shoppingCartItemStatusRepository: ShoppingCartItemStatusRepository,
                          @Autowired val productRepository: ProductRepository) {

    private fun getShoppingCartGuaranteed(userId: Long): ShoppingCartEntity {
        val shoppingCart = shoppingCartRepository.findByUserId(userId)
        return if (shoppingCart != null) shoppingCart
        else {
            val insertShoppingCart = ShoppingCartEntity(userId)
            shoppingCartRepository.save(insertShoppingCart)
        }
    }

    fun getShoppingCart(request: GetShoppingCartRequest) =
            this.getShoppingCartGuaranteed(request.userId).mapToModel()

    fun updateProduct(request: UpdateProductRequest) {
        val shoppingCart = this.getShoppingCartGuaranteed(request.userId)
        val existingShoppingCartItem = shoppingCartItemRepository
                .findByShoppingCartAndProduct(
                        shoppingCart.shoppingCartId!!,
                        request.productId)
        if (existingShoppingCartItem == null) {
            throw NonExistentShoppingCartItemException("Shopping cart item does not exist")
        } else {
            existingShoppingCartItem.amount = request.amount
            shoppingCartItemRepository.save(existingShoppingCartItem)
        }
    }

    fun addProduct(request: AddProductRequest) {
        val shoppingCart = this.getShoppingCartGuaranteed(request.userId)
        val product = productRepository.findById(request.productId).orElseGet(null)
        val shoppingCartItemStatus = shoppingCartItemStatusRepository.findById(ShoppingCartItemStatus.CREATED.id).orElseGet(null)

        val existingShoppingCartItem = shoppingCartItemRepository
                .findByShoppingCartAndProduct(
                        shoppingCart.shoppingCartId!!,
                        product.productId!!)

        if (existingShoppingCartItem != null) {
            existingShoppingCartItem.amount = existingShoppingCartItem.amount?.plus(request.amount)

            shoppingCartItemRepository.save(existingShoppingCartItem)
        } else {
            val shoppingCartItemInsert =
                    ShoppingCartItemEntity(
                            shoppingCart,
                            shoppingCartItemStatus,
                            product,
                            request.amount)

            shoppingCartItemRepository.save(shoppingCartItemInsert)
        }

        if (shoppingCart.items.isEmpty()) {
            shoppingCartRepository.flush()
            shoppingCartRepository.clear()
        }
    }

    fun deleteProduct(request: DeleteProductRequest) {
        val shoppingCart = this.getShoppingCartGuaranteed(request.userId)
        val existingShoppingCartItem = shoppingCartItemRepository
                .findByShoppingCartAndProduct(
                        shoppingCart.shoppingCartId!!,
                        request.productId)
        if (existingShoppingCartItem == null) {
            throw NonExistentShoppingCartItemException("Shopping cart item does not exist")
        } else {
            val shoppingCartItemStatus = shoppingCartItemStatusRepository
                    .findById(ShoppingCartItemStatus.ARCHIVED.id).orElseGet(null)
            existingShoppingCartItem.shoppingCartItemStatus = shoppingCartItemStatus
            shoppingCartItemRepository.save(existingShoppingCartItem)
        }
    }
}