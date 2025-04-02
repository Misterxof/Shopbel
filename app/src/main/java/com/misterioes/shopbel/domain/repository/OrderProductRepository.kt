package com.misterioes.shopbel.domain.repository

import com.misterioes.shopbel.data.entity.OrderProduct
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.OrderProductWithDetails
import kotlinx.coroutines.flow.Flow

interface OrderProductRepository {
    fun getCartProductWithDetailsById(packId: Long): Flow<CartProductWithDetails>

    fun getAllOrderProducts() : Flow<List<OrderProduct>>

    fun getAllOrderProductsByOrderId(orderId: String, userId: String) : Flow<List<OrderProduct>>

    fun getOrderProductsWithDetailsByIds(packIds: List<Long>): Flow<List<OrderProductWithDetails>>

    suspend fun addOrderProduct(orderProduct: OrderProduct)

    suspend fun deleteOrderProduct(orderProduct: OrderProduct)
}