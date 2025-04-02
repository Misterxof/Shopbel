package com.misterioes.shopbel.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.misterioes.shopbel.data.entity.Barcode
import kotlinx.coroutines.flow.Flow

@Dao
interface BarcodeDao {
    @Query("SELECT * from barcode")
    fun getAllBarcode(): Flow<List<Barcode>>

    @Query("SELECT * from barcode WHERE id = :id")
    fun getBarcodeById(id: Long): Flow<Barcode>

    @Insert
    suspend fun addBarcode(barcode: Barcode)

    @Delete
    suspend fun deleteBarcode(barcode: Barcode)
}