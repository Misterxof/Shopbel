package com.misterioes.shopbel.data.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.misterioes.shopbel.data.dao.ProductDao
import com.misterioes.shopbel.data.entity.Barcode
import com.misterioes.shopbel.data.entity.Pack
import com.misterioes.shopbel.data.entity.PackPrice
import com.misterioes.shopbel.data.entity.Unit
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProductRepositoryImplementation @Inject constructor(private val productDao: ProductDao):ProductRepository {
    override fun getAllProductsWithDetails(): Flow<List<ProductWithDetails>> {
        return productDao.getAllProductsWithDetails()
    }

    override suspend fun insertProduct(pack: Pack, price: PackPrice, unit: Unit, barcode: Barcode) {
        productDao.insertProduct(pack, price, unit, barcode)
    }

    override suspend fun syncProducts() {
        loadProductsFromFirestore()
    }

    suspend fun loadProductsFromFirestore() {
        try {
            val db = Firebase.firestore
            val packs = db.collection("pack")
                .get()
                .await()
                .toObjects(Pack::class.java)

            packs.forEach {
                val packPrice = db.collection("pack_price")
                    .whereEqualTo("pack_id", it.id)
                    .get()
                    .await()
                    .toObjects(PackPrice::class.java)

                val unit = db.collection("unit")
                    .whereEqualTo("id", it.unit_id)
                    .get()
                    .await()
                    .toObjects(Unit::class.java)

                val barcode = db.collection("barcode")
                    .whereEqualTo("pack_id", it.id)
                    .get()
                    .await()
                    .toObjects(Barcode::class.java)
                if (it != null && packPrice.isNotEmpty() && unit.isNotEmpty() && barcode.isNotEmpty()) {
                    insertProduct(it, packPrice[0], unit[0], barcode[0])
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}