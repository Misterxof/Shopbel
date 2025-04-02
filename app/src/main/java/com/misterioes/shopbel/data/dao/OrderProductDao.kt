package com.misterioes.shopbel.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.misterioes.shopbel.data.entity.OrderProduct
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.OrderProductWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderProductDao {
    @Transaction
    @Query("SELECT * FROM pack WHERE id == :packId")
    fun getCartProductWithDetailsById(packId: Long): Flow<CartProductWithDetails>

    @Transaction
    @Query("SELECT * FROM pack WHERE id IN (:packIds)")
    fun getOrderProductsWithDetailsByIds(packIds: List<Long>): Flow<List<OrderProductWithDetails>>

    @Query("SELECT * FROM order_product")
    fun getAllOrderProducts() : Flow<List<OrderProduct>>

    @Query("SELECT * FROM order_product WHERE order_id == :orderId AND user_id == :userId")
    fun getAllOrderProductsByOrderId(orderId: String, userId: String) : Flow<List<OrderProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrderProduct(orderProduct: OrderProduct)

    @Delete
    fun deleteOrderProduct(orderProduct: OrderProduct)
}