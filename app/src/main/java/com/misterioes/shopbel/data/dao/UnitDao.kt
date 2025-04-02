package com.misterioes.shopbel.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.misterioes.shopbel.data.entity.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface UnitDao {
    @Query("SELECT * from unit")
    fun getAllUnit(): Flow<List<Unit>>

    @Query("SELECT * from unit WHERE id = :id")
    fun getUnitById(id: Long): Flow<Unit>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUnit(unit: Unit)

    @Delete
    suspend fun deleteUnit(unit: Unit)
}