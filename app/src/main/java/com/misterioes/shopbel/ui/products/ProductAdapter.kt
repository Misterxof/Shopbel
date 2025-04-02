package com.misterioes.shopbel.ui.products

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.databinding.ProductsListItemBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ProductAdapter @AssistedInject constructor(
    @Assisted val onProductClick: (product: ProductWithDetails) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {
    private val products = mutableListOf<ProductWithDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding =
            ProductsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding, onProductClick)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(products[position])
    }

    fun setProducts(list: List<ProductWithDetails>) {
        list.forEach {
            if (!products.contains(it)) {
                products.add(it)
                notifyDataSetChanged()
            }
        }
    }

    inner class ProductHolder(
        binding: ProductsListItemBinding,
        val onProductClick: (product: ProductWithDetails) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var product: ProductWithDetails
        val productName = binding.productName
        val productSum = binding.productSum
        val productSumWithBonus = binding.productSumBonus
        val productType = binding.productType

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(product: ProductWithDetails) {
            this.product = product
            productName.text = product.pack.name
            productSum.text = "${product.packPrice!!.price / 100} BYN"
            productSum.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            productSumWithBonus.text =
                "${(product.packPrice!!.price - product.packPrice!!.bonus) / 100} BYN"
            productType.text = "for ${product.pack.quant} ${product.unit!!.name}"
        }

        override fun onClick(p0: View?) {
            onProductClick(product)
        }
    }
}