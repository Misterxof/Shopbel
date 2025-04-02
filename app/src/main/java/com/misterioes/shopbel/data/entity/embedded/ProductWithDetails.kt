package com.misterioes.shopbel.data.entity.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.misterioes.shopbel.data.entity.Barcode
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.data.entity.PackPrice
import com.misterioes.shopbel.data.entity.Unit
import java.io.Serializable

data class ProductWithDetails(
    @Embedded val pack: Pack,
    @Relation(
        parentColumn = "id",
        entityColumn = "pack_id"
    )
    val packPrice: PackPrice?,
    @Relation(
        parentColumn = "unit_id",
        entityColumn = "id"
    )
    val unit: Unit?,
    @Relation(
        parentColumn = "id",
        entityColumn = "pack_id"
    )
    val barcode: Barcode?
) : Serializable
