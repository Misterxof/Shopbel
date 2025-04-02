package com.misterioes.shopbel.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.misterioes.shopbel.data.entity.Barcode
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.data.entity.PackPrice
import com.misterioes.shopbel.data.entity.Unit
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Transaction
    @Query("SELECT * FROM pack")
    fun getAllProductsWithDetails(): Flow<List<ProductWithDetails>>

    @Transaction
    suspend fun insertProduct(
        pack: Pack,
        price: PackPrice,
        unit: Unit,
        barcode: Barcode
    ) {
        insertUnit(unit)
        insertPack(pack)
        insertPrice(price)
        insertBarcode(barcode)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPack(pack: Pack)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrice(price: PackPrice)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBarcode(barcode: Barcode)
}