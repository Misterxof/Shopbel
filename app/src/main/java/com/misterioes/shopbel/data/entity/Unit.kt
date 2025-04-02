package com.misterioes.shopbel.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "unit")
data class Unit(
    @PrimaryKey val id: Long,
    val name: String,
) : Serializable {
    constructor() : this(0, "")
}
