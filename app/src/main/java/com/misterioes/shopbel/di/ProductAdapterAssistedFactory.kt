package com.misterioes.shopbel.di

import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.ui.products.ProductAdapter
import dagger.assisted.AssistedFactory

@AssistedFactory
interface ProductAdapterAssistedFactory {
    fun create(
        onProductClick: (product: ProductWithDetails) -> Unit
    ): ProductAdapter
}