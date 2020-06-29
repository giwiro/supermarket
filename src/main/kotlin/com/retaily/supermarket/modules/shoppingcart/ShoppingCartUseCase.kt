package com.retaily.supermarket.modules.shoppingcart

import com.retaily.supermarket.database.entities.ProductRepository
import com.retaily.supermarket.database.entities.ShoppingCartItemEntity
import com.retaily.supermarket.database.entities.ShoppingCartItemRepository
import com.retaily.supermarket.database.entities.ShoppingCartItemStatusRepository
import com.retaily.supermarket.database.entities.ShoppingCartRepository
import com.retaily.supermarket.models.ShoppingCartItemStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ShoppingCartUseCase(
    @Autowired val shoppingCartRepository: ShoppingCartRepository,
    @Autowired val shoppingCartItemRepository: ShoppingCartItemRepository,
    @Autowired val shoppingCartItemStatusRepository: ShoppingCartItemStatusRepository,
    @Autowired val productRepository: ProductRepository,
    @Autowired val shoppingCartService: ShoppingCartService
) {
    fun getShoppingCart(request: GetShoppingCartRequest) =
        shoppingCartService.getShoppingCartGuaranteed(request.userId).mapToModel()

    fun updateProduct(request: UpdateProductRequest) {
        val shoppingCart = shoppingCartService.getShoppingCartGuaranteed(request.userId)
        val existingShoppingCartItem = shoppingCartItemRepository
            .findByShoppingCartAndProduct(
                shoppingCart.shoppingCartId!!,
                request.productId
            )
        if (existingShoppingCartItem == null) {
            throw NonExistentShoppingCartItemException("Shopping cart item does not exist")
        } else {
            existingShoppingCartItem.amount = request.amount
            shoppingCartItemRepository.save(existingShoppingCartItem)
        }
        shoppingCartRepository.flush()
        shoppingCartRepository.clear()
    }

    fun addProduct(request: AddProductRequest) {
        val shoppingCart = shoppingCartService.getShoppingCartGuaranteed(request.userId)
        val product = productRepository.findById(request.productId).orElseGet(null)
        val shoppingCartItemStatus = shoppingCartItemStatusRepository.findById(ShoppingCartItemStatus.CREATED.id).orElseGet(null)

        val existingShoppingCartItem = shoppingCartItemRepository
            .findByShoppingCartAndProduct(
                shoppingCart.shoppingCartId!!,
                product.productId!!
            )

        if (existingShoppingCartItem != null) {
            existingShoppingCartItem.amount = existingShoppingCartItem.amount?.plus(request.amount)

            shoppingCartItemRepository.save(existingShoppingCartItem)
        } else {
            val shoppingCartItemInsert =
                ShoppingCartItemEntity(
                    shoppingCart,
                    shoppingCartItemStatus,
                    product,
                    request.amount
                )

            shoppingCartItemRepository.save(shoppingCartItemInsert)
        }

        shoppingCartRepository.flush()
        shoppingCartRepository.clear()
    }

    fun deleteProduct(request: DeleteProductRequest) {
        val shoppingCart = shoppingCartService.getShoppingCartGuaranteed(request.userId)
        val existingShoppingCartItem = shoppingCartItemRepository
            .findByShoppingCartAndProduct(
                shoppingCart.shoppingCartId!!,
                request.productId
            )
        if (existingShoppingCartItem == null) {
            throw NonExistentShoppingCartItemException("Shopping cart item does not exist")
        } else {
            val shoppingCartItemStatus = shoppingCartItemStatusRepository
                .findById(ShoppingCartItemStatus.ARCHIVED.id).orElseGet(null)
            existingShoppingCartItem.shoppingCartItemStatus = shoppingCartItemStatus
            shoppingCartItemRepository.save(existingShoppingCartItem)
        }

        shoppingCartRepository.flush()
        shoppingCartRepository.clear()
    }
}
