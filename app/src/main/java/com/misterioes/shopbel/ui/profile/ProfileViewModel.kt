package com.misterioes.shopbel.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterioes.shopbel.data.entity.Order
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.domain.usecase.OrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val orderUseCase: OrderUseCase
): ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders

    init {
        loadOrdersFromRoom()
    }

    fun loadOrdersFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            orderUseCase.getAllOrdersFromRoom().collect {
                _orders.value = it
            }
        }
    }

    fun loadOrdersFromFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            orderUseCase.syncOrdersWithFirestore()
        }
    }
}