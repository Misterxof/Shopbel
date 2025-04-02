package com.misterioes.shopbel.ui.orders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterioes.shopbel.data.entity.Order
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.OrderProductWithDetails
import com.misterioes.shopbel.domain.model.Status
import com.misterioes.shopbel.domain.repository.CartRepository
import com.misterioes.shopbel.domain.usecase.OrderUseCase
import com.misterioes.shopbel.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel  @Inject constructor(
    private val orderUseCase: OrderUseCase
): ViewModel() {
    private val _products = MutableStateFlow<List<OrderProductWithDetails>>(emptyList())
    val products: StateFlow<List<OrderProductWithDetails>> = _products
    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<Double> = _price
    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    fun loadProducts(order: Order) {
        loadOrderProducts(order)
        syncOrderProductsWithFirestore(order)
    }

    fun loadOrderProducts(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {

            orderUseCase.getAllOrderProductsByOrderId(order.id.toString()).collect {
                var newPrice = 0.0
                var count = 0
                _products.value = it
                if (it.isEmpty()) {
                    _price.postValue(0.0)
                    _count.postValue(0)
                }
                it.forEach {
                    newPrice += (it.product.packPrice!!.price - it.product.packPrice.bonus) * it.orderProduct!!.count / 100
                    count += it.orderProduct.count
                }
                _price.postValue(newPrice)
                _count.postValue(count)
            }
        }
    }

    fun syncOrderProductsWithFirestore(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderUseCase.syncOrderProductsWithFirestore(order.id)
        }
    }

    fun cancelAllOperations() {
        viewModelScope.cancel()
    }
}