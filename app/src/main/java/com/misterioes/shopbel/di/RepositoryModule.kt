package com.misterioes.shopbel.di

import com.misterioes.shopbel.data.repository.BarcodeRepositoryImplementation
import com.misterioes.shopbel.data.repository.CartRepositoryImplementation
import com.misterioes.shopbel.data.repository.OrderProductRepositoryImplementation
import com.misterioes.shopbel.data.repository.OrderRepositoryImplementation
import com.misterioes.shopbel.data.repository.PackPriceRepositoryImplementation
import com.misterioes.shopbel.data.repository.PackRepositoryImplementation
import com.misterioes.shopbel.data.repository.ProductRepositoryImplementation
import com.misterioes.shopbel.data.repository.UnitRepositoryImplementation
import com.misterioes.shopbel.domain.repository.BarcodeRepository
import com.misterioes.shopbel.domain.repository.CartRepository
import com.misterioes.shopbel.domain.repository.OrderProductRepository
import com.misterioes.shopbel.domain.repository.OrderRepository
import com.misterioes.shopbel.domain.repository.PackPriceRepository
import com.misterioes.shopbel.domain.repository.PackRepository
import com.misterioes.shopbel.domain.repository.ProductRepository
import com.misterioes.shopbel.domain.repository.UnitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun getPackRepository(packRepositoryImplementation: PackRepositoryImplementation): PackRepository

    @Binds
    @Singleton
    fun getBarcodeRepository(barcodeRepositoryImplementation: BarcodeRepositoryImplementation): BarcodeRepository

    @Binds
    @Singleton
    fun getPackPriceRepository(packPriceRepositoryImplementation: PackPriceRepositoryImplementation): PackPriceRepository

    @Binds
    @Singleton
    fun getUnitRepository(unitRepositoryImplementation: UnitRepositoryImplementation): UnitRepository

    @Binds
    @Singleton
    fun getProductRepository(productRepositoryImplementation: ProductRepositoryImplementation): ProductRepository

    @Binds
    @Singleton
    fun getCartRepository(cartRepositoryImplementation: CartRepositoryImplementation): CartRepository

    @Binds
    @Singleton
    fun getOrderRepository(orderRepositoryImplementation: OrderRepositoryImplementation): OrderRepository

    @Binds
    @Singleton
    fun getOrderProductRepository(orderProductRepositoryImplementation: OrderProductRepositoryImplementation): OrderProductRepository
}