package com.misterioes.shopbel.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.misterioes.shopbel.data.entity.CartProduct
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartProductsWithDetailsByIds(packIds: List<Long>): Flow<List<CartProductWithDetails>>

    fun getAllCartProducts(): Flow<List<CartProduct>>

    suspend fun addCartProduct(cartProduct: CartProduct)

    suspend fun deleteCartProduct(cartProduct: CartProduct)

    suspend fun clearCartProducts()
}