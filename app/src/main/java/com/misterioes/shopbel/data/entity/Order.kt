package com.misterioes.shopbel.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName
import java.io.Serializable
import java.util.Date

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey val id: Long,
    @PropertyName("user_id")
    @ColumnInfo(name = "user_id")
    val userId: String,
    val date: Date
): Serializable {
    constructor() : this(0, "", Date())
}