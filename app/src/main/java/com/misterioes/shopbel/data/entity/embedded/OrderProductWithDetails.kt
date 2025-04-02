package com.misterioes.shopbel.data.entity.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.misterioes.shopbel.data.entity.OrderProduct

data class OrderProductWithDetails(
    @Embedded val product: ProductWithDetails,
    @Relation(
        parentColumn = "id",
        entityColumn = "pack_id"
    )
    val orderProduct: OrderProduct?
)
