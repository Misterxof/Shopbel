package com.misterioes.shopbel.di

import com.misterioes.shopbel.data.entity.Order
import com.misterioes.shopbel.ui.profile.ProfileAdapter
import dagger.assisted.AssistedFactory

@AssistedFactory
interface OrderAdapterAssistedFactory {
    fun create(
        onOrderClick: (order: Order) -> Unit
    ): ProfileAdapter
}