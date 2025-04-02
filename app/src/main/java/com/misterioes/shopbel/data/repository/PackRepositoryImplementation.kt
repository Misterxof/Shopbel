package com.misterioes.shopbel.data.repository

import com.misterioes.shopbel.data.dao.PackDao
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.domain.repository.PackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PackRepositoryImplementation @Inject constructor(
    private val packDao: PackDao,
) : PackRepository {
    override suspend fun getAllPacks(): Flow<List<Pack>> {
        return packDao.getAllPacks()
    }

    override suspend fun getPackById(id: Long): Flow<Pack> {
        return packDao.getPackById(id)
    }

    override suspend fun addPack(pack: Pack) {
        packDao.addPack(pack)
    }

    override suspend fun deletePack(pack: Pack) {
        packDao.deletePack(pack)
    }
}