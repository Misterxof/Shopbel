package com.misterioes.shopbel.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterioes.shopbel.data.entity.Order
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.domain.model.Status
import com.misterioes.shopbel.domain.model.UserInfo
import com.misterioes.shopbel.domain.usecase.OrderUseCase
import com.misterioes.shopbel.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
    private val orderUseCase: OrderUseCase
    ): ViewModel() {
    private val _products = MutableStateFlow<List<CartProductWithDetails>>(emptyList())
    val products: StateFlow<List<CartProductWithDetails>> = _products
    private val _state = MutableStateFlow<Status>(Status.Loading(true))
    val state: StateFlow<Status> = _state
    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<Double> = _price
    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    init {
        loadCartProducts()
    }

    fun loadCartProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.loadCartProducts().collect {
                var newPrice = 0.0
                var count = 0
                _products.value = it
                if (it.isEmpty()) {
                    _price.postValue(0.0)
                    _count.postValue(0)
                }
                it.forEach {
                    newPrice += (it.product.packPrice!!.price - it.product.packPrice.bonus) * it.cartProduct!!.count / 100
                    count += it.cartProduct.count
                }
                _price.postValue(newPrice)
                _count.postValue(count)
            }
        }
    }

    fun sendOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            val lastId = (orderUseCase.getLastOrderId() ?: 0L) + 1
            val order = Order(lastId, UserInfo.user!!.id, Date())

            createOrder(order).collect {
                _state.value = it
                if (it is Status.Success) {
                    orderUseCase.createNewOrderProducts(products.value, order).collect {
                        _state.value = it
                        if (it is Status.Success) {
                            _state.value = Status.Success("clear")
                            _products.value = emptyList()
                            productUseCase.clearCart()
                        }
                    }
                }
            }
        }
    }

    suspend fun createOrder(order: Order): Flow<Status> {
        return orderUseCase.createNewOrder(order)
    }
}