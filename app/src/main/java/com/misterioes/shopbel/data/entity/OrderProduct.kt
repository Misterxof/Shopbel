package com.misterioes.shopbel.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_product")
data class OrderProduct(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "pack_id")
    val packId: Long,
    @ColumnInfo(name = "user_id")
    val userId: Long,
    @ColumnInfo(name = "order_id")
    val orderId: Long,
    val count: Int
) {
    constructor() : this("0", 0, 0, 0, 0)
}
