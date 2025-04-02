package com.misterioes.shopbel.data.repository

import com.misterioes.shopbel.data.dao.UnitDao
import com.misterioes.shopbel.data.entity.Unit
import com.misterioes.shopbel.domain.repository.UnitRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnitRepositoryImplementation @Inject constructor(val unitDao: UnitDao): UnitRepository {
    override suspend fun getAllUnit(): Flow<List<Unit>> {
        return unitDao.getAllUnit()
    }

    override suspend fun getUnitById(id: Long): Flow<Unit> {
        return unitDao.getUnitById(id)
    }

    override suspend fun addUnit(unit: Unit) {
        unitDao.addUnit(unit)
    }

    override suspend fun deleteUnit(unit: Unit) {
        unitDao.deleteUnit(unit)
    }
}