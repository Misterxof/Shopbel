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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            orderUseCase.getAllOrderProducts().collect {
                Log.e("///1////", it.toString())
            }
        }
    }

    fun loadProducts(order: Order) {
        loadOrderProducts(order)
        syncOrderProductsWithFirestore(order)
    }

    fun loadOrderProducts(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {

            orderUseCase.getAllOrderProductsByOrderId(order.id.toString()).collect {
                _products.value = it
                _count.postValue(it.size)
                Log.e("///2////", it.size.toString())
                it.forEach {
                    val cp = it.orderProduct.toString()
                    val c = it.product.toString()
                    _price.postValue(_price.value?.plus((it.product.packPrice!!.price - it.product.packPrice.bonus) / 100))
                    Log.e("///////", cp)
                    Log.e("///////", c)
                }
            }
        }
    }

    fun syncOrderProductsWithFirestore(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            orderUseCase.syncOrderProductsWithFirestore(order.id)
        }
    }
}