package com.misterioes.shopbel.di

import android.content.Context
import androidx.room.Room
import com.misterioes.shopbel.data.AppDatabase
import com.misterioes.shopbel.data.FirebaseService
import com.misterioes.shopbel.data.dao.BarcodeDao
import com.misterioes.shopbel.data.dao.CartDao
import com.misterioes.shopbel.data.dao.OrderDao
import com.misterioes.shopbel.data.dao.OrderProductDao
import com.misterioes.shopbel.data.dao.PackDao
import com.misterioes.shopbel.data.dao.PackPriceDao
import com.misterioes.shopbel.data.dao.ProductDao
import com.misterioes.shopbel.data.dao.UnitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {
    @Provides
    @Singleton
    fun getAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "shopbel").build()
    }

    @Provides
    @Singleton
    fun getBarcodeDao(appDatabase: AppDatabase): BarcodeDao {
        return appDatabase.getBarcodeDao()
    }

    @Provides
    @Singleton
    fun getPackDao(appDatabase: AppDatabase): PackDao {
        return appDatabase.getPackDao()
    }

    @Provides
    @Singleton
    fun getPackPriceDao(appDatabase: AppDatabase): PackPriceDao {
        return appDatabase.getPackPriceDao()
    }

    @Provides
    @Singleton
    fun getUnitDao(appDatabase: AppDatabase): UnitDao {
        return appDatabase.getUnitDao()
    }

    @Provides
    @Singleton
    fun getProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.getProductDao()
    }

    @Provides
    @Singleton
    fun getCartDao(appDatabase: AppDatabase): CartDao {
        return appDatabase.getCartDao()
    }

    @Provides
    @Singleton
    fun getOrderDao(appDatabase: AppDatabase): OrderDao {
        return appDatabase.getOrderDao()
    }

    @Provides
    @Singleton
    fun getOrderProductDao(appDatabase: AppDatabase): OrderProductDao {
        return appDatabase.getOrderProductDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseService(
        @ApplicationContext appContext: Context
    ): FirebaseService {
        return Retrofit.Builder()
            .baseUrl("https://firestore.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FirebaseService::class.java)
    }
}