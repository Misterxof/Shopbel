package com.misterioes.shopbel.domain.repository

import com.misterioes.shopbel.data.entity.Barcode
import kotlinx.coroutines.flow.Flow

interface BarcodeRepository {
    suspend fun getAllBarcode(): Flow<List<Barcode>>

    suspend fun getBarcodeById(id: Long): Flow<Barcode>

    suspend fun addBarcode(barcode: Barcode)

    suspend fun deleteBarcode(barcode: Barcode)
}