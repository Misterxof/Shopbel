package com.misterioes.shopbel.data.repository

import com.misterioes.shopbel.data.dao.CartDao
import com.misterioes.shopbel.data.entity.CartProduct
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImplementation @Inject constructor(private val cartDao: CartDao): CartRepository {
    override fun getCartProductsWithDetailsByIds(packIds: List<Long>): Flow<List<CartProductWithDetails>> {
        return cartDao.getCartProductsWithDetailsByIds(packIds)
    }

    override fun getAllCartProducts(): Flow<List<CartProduct>> {
        return cartDao.getAllCartProducts()
    }

    override suspend fun addCartProduct(cartProduct: CartProduct) {
        cartDao.addCartProduct(cartProduct)
    }

    override suspend fun deleteCartProduct(cartProduct: CartProduct) {
        cartDao.deleteCartProduct(cartProduct)
    }

    override suspend fun clearCartProducts() {
        cartDao.clearCartProducts()
    }
}