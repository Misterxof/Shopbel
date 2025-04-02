package com.misterioes.shopbel.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.misterioes.shopbel.data.entity.CartProduct
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Transaction
    @Query("SELECT * FROM pack WHERE id IN (:packIds)")
    fun getCartProductsWithDetailsByIds(packIds: List<Long>): Flow<List<CartProductWithDetails>>

    @Query("SELECT * FROM cart_product")
    fun getAllCartProducts(): Flow<List<CartProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCartProduct(cartProduct: CartProduct)

    @Delete
    suspend fun deleteCartProduct(cartProduct: CartProduct)

    @Query("DELETE FROM cart_product")
    suspend fun clearCartProducts()
}