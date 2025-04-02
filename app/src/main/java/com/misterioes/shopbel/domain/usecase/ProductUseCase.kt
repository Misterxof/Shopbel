package com.misterioes.shopbel.domain.usecase

import android.util.Log
import com.misterioes.shopbel.data.entity.CartProduct
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.domain.model.UserInfo
import com.misterioes.shopbel.domain.repository.CartRepository
import com.misterioes.shopbel.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProductUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) {
    suspend fun loadProductsFromRoom(): Flow<List<ProductWithDetails>> {
        return productRepository.getAllProductsWithDetails()
    }

    suspend fun syncProductsWithFirestore() {
        productRepository.syncProducts()
    }

    suspend fun clearCart() {
        cartRepository.clearCartProducts()
    }

    suspend fun saveToCart(product: ProductWithDetails, quantity: Int) {
        val cartProduct = CartProduct(product.pack.id, UserInfo.user!!.id, quantity)
        cartRepository.addCartProduct(cartProduct)
    }

    suspend fun getAllCartProducts(): Flow<List<CartProduct>> {
        return cartRepository.getAllCartProducts()
    }

    suspend fun loadCartProducts(): Flow<List<CartProductWithDetails>> {
        return cartRepository.getAllCartProducts()
            .map { it.map { cartProduct -> cartProduct.packId } }
            .flatMapLatest { ids -> cartRepository.getCartProductsWithDetailsByIds(ids) }
    }
}