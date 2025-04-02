package com.misterioes.shopbel.data.entity.embedded

import androidx.room.Embedded
import androidx.room.Relation
import com.misterioes.shopbel.data.entity.CartProduct

data class CartProductWithDetails (
    @Embedded val product: ProductWithDetails,
    @Relation(
        parentColumn = "id",
        entityColumn = "pack_id"
    )
    val cartProduct: CartProduct?
)