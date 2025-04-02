package com.misterioes.shopbel.data.repository

import com.misterioes.shopbel.data.dao.OrderDao
import com.misterioes.shopbel.data.entity.Order
import com.misterioes.shopbel.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderRepositoryImplementation @Inject constructor(private val orderDao: OrderDao) : OrderRepository {
    override fun getAllOrders(): Flow<List<Order>> {
        return orderDao.getAllOrders()
    }

    override suspend fun addOrder(order: Order) {
        orderDao.addOrder(order)
    }

    override suspend fun deleteOrder(order: Order) {
        orderDao.deleteOrder(order)
    }
}