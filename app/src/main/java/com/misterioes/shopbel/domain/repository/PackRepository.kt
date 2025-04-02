package com.misterioes.shopbel.domain.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface PackRepository {
    suspend fun getAllPacks(): Flow<List<Pack>>

    suspend fun getPackById(id: Long): Flow<Pack>

    suspend fun addPack(pack: Pack)

    suspend fun deletePack(pack: Pack)
}