package com.misterioes.shopbel.domain.repository

import androidx.room.Transaction
import com.misterioes.shopbel.data.entity.Barcode
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.data.entity.PackPrice
import com.misterioes.shopbel.data.entity.Unit
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProductsWithDetails(): Flow<List<ProductWithDetails>>

    suspend fun insertProduct(
        pack: Pack,
        price: PackPrice,
        unit: Unit,
        barcode: Barcode
    )

    suspend fun syncProducts()
}