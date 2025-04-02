package com.misterioes.shopbel.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
        tableName = "cart_product",
        primaryKeys = ["pack_id", "user_id"]
)
data class CartProduct(
        @ColumnInfo(name = "pack_id")
        val packId: Long,
        @ColumnInfo(name = "user_id")
        val userId: Long,
        val count: Int
)
