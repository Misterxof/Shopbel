package com.misterioes.shopbel.domain.usecase

import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.misterioes.shopbel.data.entity.CartProduct
import com.misterioes.shopbel.data.entity.Order
import com.misterioes.shopbel.data.entity.OrderProduct
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.data.entity.Unit
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.OrderProductWithDetails
import com.misterioes.shopbel.domain.model.Status
import com.misterioes.shopbel.domain.model.UserInfo
import com.misterioes.shopbel.domain.repository.CartRepository
import com.misterioes.shopbel.domain.repository.OrderProductRepository
import com.misterioes.shopbel.domain.repository.OrderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject

class OrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val orderProductRepository: OrderProductRepository,
    private val cartRepository: CartRepository
) {
    val db = Firebase.firestore

    suspend fun getAllOrderProducts(): Flow<List<OrderProduct>> {
        return orderProductRepository.getAllOrderProducts()
    }

    suspend fun getAllOrdersFromRoom(): Flow<List<Order>> {
        return orderRepository.getAllOrders()
    }

    suspend fun getAllOrderProductsByOrderId(orderId: String): Flow<List<OrderProductWithDetails>> {
        Log.e("///q////",orderId)
        return orderProductRepository.getAllOrderProductsByOrderId(orderId)
            .map { Log.e("///3////", it.toString())
             it}
            .filter { it.isNotEmpty() }
            .map { it.map { product -> product.packId } }
            .flatMapConcat { ids -> orderProductRepository.getOrderProductsWithDetailsByIds(ids) }
    }

    suspend fun syncOrderProductsWithFirestore(orderId: Long) {
        val orderProducts = db.collection("user").document(UserInfo.user!!.id)
            .collection("orders_products")
            .whereEqualTo("order_id", orderId)
            .get()
            .await()
            .map { doc ->
            OrderProduct(
                id = doc.id,
                packId = doc.getLong("pack_id") ?: 0,
                userId = doc.getString("user_id") ?: "",
                orderId = doc.getLong("order_id") ?: 0,
                count = doc.getLong("count")?.toInt() ?: 0
            )
        }

        Log.e("///4//// o_id", orderId.toString())
        Log.e("///4//// sze", orderProducts.size.toString())
        orderProducts.forEach {
            Log.e("///4////", it.toString())
            orderProductRepository.addOrderProduct(it)
        }
    }

    suspend fun syncOrdersWithFirestore() {
        val orders = db.collection("user").document(UserInfo.user!!.id)
            .collection("orders")
            .get()
            .await()
            .map { doc ->
                Order(
                    id = doc.getLong("id") ?: 0,
                    userId = doc.getString("user_id") ?: "",
                    date = doc.getDate("date") ?: Date()
                )
            }

        orders.forEach {
            orderRepository.addOrder(it)
        }
    }

    suspend fun createNewOrder(order: Order) = callbackFlow {

        val orderData = hashMapOf(
            "id" to order.id,
            "user_id" to order.userId,
            "date" to order.date
        )

        db.collection("user").document(order.userId.toString())
            .collection("orders")
            .document(order.id.toString())
            .set(orderData)
            .addOnSuccessListener { documentReference ->
                addOrder(order)
                trySend(Status.Success(order.id.toString()))
                close()
            }
            .addOnFailureListener { e ->
                trySend(Status.Error(e.message!!))
                close()
            }

        awaitClose {}
    }

    suspend fun createNewOrderProducts(cartProducts: List<CartProductWithDetails>, order: Order) =
        callbackFlow {
            val userId = order.userId
            val orderProducts = mutableListOf<OrderProduct>()

            val userOrdersRef = db.collection("user")
                .document(userId.toString())
                .collection("orders_products")

            val batch = db.batch()

            cartProducts.forEach { product ->
                val productDocRef = userOrdersRef.document()
                val orderProduct =
                    OrderProduct(productDocRef.id, product.cartProduct!!.packId, userId, order.id, product.cartProduct.count)
                orderProducts.add(orderProduct)
                val productData = hashMapOf(
                    "id" to orderProduct.id,
                    "pack_id" to orderProduct.packId,
                    "user_id" to orderProduct.userId,
                    "order_id" to orderProduct.orderId,
                    "count" to orderProduct.count
                )
                batch.set(productDocRef, productData)
            }

            batch.commit()
                .addOnSuccessListener {
                    addOrderProducts(orderProducts)
                    trySend(Status.Success("Products saved successfully"))
                    close()
                }
                .addOnFailureListener { e ->
                    trySend(Status.Error(e.message ?: "Unknown error"))
                    close()
                }

            awaitClose {}
        }

    fun addOrder(order: Order) {
        CoroutineScope(Job() + Dispatchers.IO).launch {
            orderRepository.addOrder(order)
        }
    }

    fun addOrderProducts(orderProducts: List<OrderProduct>) {
        Log.e("OrderUseCase 1", orderProducts.toString())
        CoroutineScope(Job() + Dispatchers.IO).launch {
            orderProducts.forEach {
                Log.e("OrderUseCase 2", it.toString())
                orderProductRepository.addOrderProduct(it)
            }
        }
    }

    suspend fun getLastOrderId(): Long? {
        val order = db.collection("user")
            .document(UserInfo.user!!.id)
            .collection("orders")
            .orderBy("date", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
            .toObjects(Order::class.java)
        return if (order.isEmpty()) {
            null
        } else {
            return order[0].id
        }
    }
}