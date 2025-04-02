package com.misterioes.shopbel.domain.model

import com.misterioes.shopbel.data.entity.Barcode
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.data.entity.PackPrice
import com.misterioes.shopbel.data.entity.Unit

data class Product(
    val pack: Pack,
    val price: PackPrice,
    val unit: Unit,
    val barcode: Barcode
)