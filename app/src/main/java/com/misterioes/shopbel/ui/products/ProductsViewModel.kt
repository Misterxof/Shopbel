package com.misterioes.shopbel.ui.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.domain.usecase.ProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(private val productUseCase: ProductUseCase): ViewModel() {
    private val _products = MutableStateFlow<List<ProductWithDetails>>(emptyList())
    val products: StateFlow<List<ProductWithDetails>> = _products

    init {
        loadProductsFromRoom()
    }

    fun loadProductsFromRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.loadProductsFromRoom().collect {
                Log.e("HomeViewModel", it.toString())
                _products.value = it
            }
        }
    }

    fun loadProductsFromFirestore() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("ViewModel", "syncProductsWithFirestore")
            productUseCase.syncProductsWithFirestore()
        }
    }

    fun saveToCart(product: ProductWithDetails, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productUseCase.saveToCart(product, quantity)
        }
    }
}