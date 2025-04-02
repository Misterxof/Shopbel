package com.misterioes.shopbel.domain.repository

import com.misterioes.shopbel.data.entity.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getAllOrders(): Flow<List<Order>>

    suspend fun addOrder(order: Order)

    suspend fun deleteOrder(order: Order)
}