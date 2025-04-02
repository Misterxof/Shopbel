package com.misterioes.shopbel.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.misterioes.shopbel.data.entity.PackPrice
import kotlinx.coroutines.flow.Flow

@Dao
interface PackPriceDao {
    @Query("SELECT * from pack_price")
    fun getAllPackPrice(): Flow<List<PackPrice>>

    @Query("SELECT * from pack_price WHERE id = :id")
    fun getPackPriceById(id: Long): Flow<PackPrice>

    @Insert
    suspend fun addPackPrice(packPrice: PackPrice)

    @Delete
    suspend fun deletePackPrice(packPrice: PackPrice)
}