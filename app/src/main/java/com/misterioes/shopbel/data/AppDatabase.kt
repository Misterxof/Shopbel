package com.misterioes.shopbel.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.misterioes.shopbel.data.dao.BarcodeDao
import com.misterioes.shopbel.data.dao.CartDao
import com.misterioes.shopbel.data.dao.OrderDao
import com.misterioes.shopbel.data.dao.OrderProductDao
import com.misterioes.shopbel.data.dao.PackDao
import com.misterioes.shopbel.data.dao.PackPriceDao
import com.misterioes.shopbel.data.dao.ProductDao
import com.misterioes.shopbel.data.dao.UnitDao
import com.misterioes.shopbel.data.entity.Barcode
import com.misterioes.shopbel.data.entity.CartProduct
import com.misterioes.shopbel.data.entity.Order
import com.misterioes.shopbel.data.entity.OrderProduct
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.data.entity.PackPrice
import com.misterioes.shopbel.data.entity.Unit

@Database(entities = [Barcode::class, Pack::class, Unit::class,
    PackPrice::class, CartProduct::class, Order::class, OrderProduct::class], version = 1)
@TypeConverters(DaoTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getBarcodeDao(): BarcodeDao
    abstract fun getPackDao(): PackDao
    abstract fun getPackPriceDao(): PackPriceDao
    abstract fun getUnitDao(): UnitDao
    abstract fun getProductDao(): ProductDao
    abstract fun getCartDao(): CartDao
    abstract fun getOrderDao(): OrderDao
    abstract fun getOrderProductDao(): OrderProductDao
}