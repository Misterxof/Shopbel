package com.misterioes.shopbel.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.misterioes.shopbel.data.entity.Unit
import kotlinx.coroutines.flow.Flow

interface UnitRepository {
    suspend fun getAllUnit(): Flow<List<Unit>>

    suspend fun getUnitById(id: Long): Flow<Unit>

    suspend fun addUnit(unit: Unit)

    suspend fun deleteUnit(unit: Unit)
}