package com.misterioes.shopbel.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.misterioes.shopbel.data.entity.PackPrice
import kotlinx.coroutines.flow.Flow

interface PackPriceRepository {
    suspend fun getAllPackPrice(): Flow<List<PackPrice>>

    suspend fun getPackPriceById(id: Long): Flow<PackPrice>

    suspend fun addPackPrice(packPrice: PackPrice)

    suspend fun deletePackPrice(packPrice: PackPrice)
}