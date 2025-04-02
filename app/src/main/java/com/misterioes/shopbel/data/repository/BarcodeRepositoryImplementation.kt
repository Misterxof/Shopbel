package com.misterioes.shopbel.data.repository

import com.misterioes.shopbel.data.dao.BarcodeDao
import com.misterioes.shopbel.data.entity.Barcode
import com.misterioes.shopbel.domain.repository.BarcodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BarcodeRepositoryImplementation @Inject constructor(val barcodeDao: BarcodeDao): BarcodeRepository {
    override suspend fun getAllBarcode(): Flow<List<Barcode>> {
        return barcodeDao.getAllBarcode()
    }

    override suspend fun getBarcodeById(id: Long): Flow<Barcode> {
        return barcodeDao.getBarcodeById(id)
    }

    override suspend fun addBarcode(barcode: Barcode) {
        barcodeDao.addBarcode(barcode)
    }

    override suspend fun deleteBarcode(barcode: Barcode) {
        barcodeDao.deleteBarcode(barcode)
    }
}