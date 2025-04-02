package com.misterioes.shopbel.data.repository

import com.misterioes.shopbel.data.dao.PackPriceDao
import com.misterioes.shopbel.data.entity.PackPrice
import com.misterioes.shopbel.domain.repository.PackPriceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PackPriceRepositoryImplementation @Inject constructor(val packPriceDao: PackPriceDao): PackPriceRepository {
    override suspend fun getAllPackPrice(): Flow<List<PackPrice>> {
        return packPriceDao.getAllPackPrice()
    }

    override suspend fun getPackPriceById(id: Long): Flow<PackPrice> {
        return packPriceDao.getPackPriceById(id)
    }

    override suspend fun addPackPrice(packPrice: PackPrice) {
        packPriceDao.addPackPrice(packPrice)
    }

    override suspend fun deletePackPrice(packPrice: PackPrice) {
        packPriceDao.deletePackPrice(packPrice)
    }
}