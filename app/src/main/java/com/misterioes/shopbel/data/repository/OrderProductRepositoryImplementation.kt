package com.misterioes.shopbel.data.repository

import com.misterioes.shopbel.data.dao.OrderProductDao
import com.misterioes.shopbel.data.entity.OrderProduct
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.OrderProductWithDetails
import com.misterioes.shopbel.domain.repository.OrderProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OrderProductRepositoryImplementation @Inject constructor(private val orderProductDao: OrderProductDao): OrderProductRepository {
    override fun getCartProductWithDetailsById(packId: Long): Flow<CartProductWithDetails> {
        return orderProductDao.getCartProductWithDetailsById(packId)
    }

    override fun getAllOrderProducts(): Flow<List<OrderProduct>> {
        return orderProductDao.getAllOrderProducts()
    }

    override fun getAllOrderProductsByOrderId(orderId: String): Flow<List<OrderProduct>> {
        return orderProductDao.getAllOrderProductsByOrderId(orderId)
    }

    override fun getOrderProductsWithDetailsByIds(packIds: List<Long>): Flow<List<OrderProductWithDetails>> {
        return orderProductDao.getOrderProductsWithDetailsByIds(packIds)
    }

    override suspend fun addOrderProduct(orderProduct: OrderProduct) {
        orderProductDao.addOrderProduct(orderProduct)
    }

    override suspend fun deleteOrderProduct(orderProduct: OrderProduct) {
        orderProductDao.deleteOrderProduct(orderProduct)
    }
}