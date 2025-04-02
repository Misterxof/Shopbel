package com.misterioes.shopbel.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.misterioes.shopbel.data.entity.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * from orders WHERE user_id = :userId")
    fun getAllOrders(userId: String): Flow<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrder(order: Order)

    @Delete
    fun deleteOrder(order: Order)
}