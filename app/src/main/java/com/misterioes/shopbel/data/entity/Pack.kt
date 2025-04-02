package com.misterioes.shopbel.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pack",
    foreignKeys = [
        ForeignKey(
            entity = Unit::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("unit_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Pack(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "unit_id")
    val unit_id: Long,
    val name: String,
    val type: Int,
    val quant: Int
) : Serializable {
    constructor() : this(0, 0, "", 0, 0)
}