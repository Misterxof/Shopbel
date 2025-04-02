package com.misterioes.shopbel.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface PackDao {
    @Query("SELECT * from pack")
    fun getAllPacks(): Flow<List<Pack>>

    @Query("SELECT * from pack WHERE id = :id")
    fun getPackById(id: Long): Flow<Pack>

    @Insert
    suspend fun addPack(pack: Pack)

    @Delete
    suspend fun deletePack(pack: Pack)
}