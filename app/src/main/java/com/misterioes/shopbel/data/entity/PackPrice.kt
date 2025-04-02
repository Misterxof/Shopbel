package com.misterioes.shopbel.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pack_price",
    foreignKeys = [
        ForeignKey(
            entity = Pack::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("pack_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ])
data class PackPrice(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "pack_id")
    val pack_id: Long,
    val price: Double,
    val bonus: Double,
) : Serializable {
    constructor() : this(0, 0, 0.0, 0.0)
}
