package com.misterioes.shopbel.ui.orders

import android.graphics.Bitmap
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.data.entity.embedded.OrderProductWithDetails
import com.misterioes.shopbel.databinding.CartProductListItemBinding
import com.misterioes.shopbel.databinding.OrderProductListItemBinding
import com.misterioes.shopbel.ui.cart.CartAdapter

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderHolder>() {
    private var products = mutableListOf<OrderProductWithDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val binding =
            OrderProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        if (position !in products.indices) return
        holder.bind(products[position])
    }

    fun setOrderProducts(list: List<OrderProductWithDetails>) {
        list.forEach {
            if (!products.contains(it)) {
                products.add(it)
                notifyDataSetChanged()
            }
        }
    }

    inner class OrderHolder(binding: OrderProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val productName = binding.cartProductName
        val productSum = binding.cartProductSum
        val productSumBonus = binding.cartProductSumBonus
        val productType = binding.cartProductType
        val cartSum = binding.cartProductTotalSum
        val cartSumBonus = binding.cartProductTotalSumBonus
        val cartType = binding.cartProductTotalType
        val cartQuantity = binding.cartProductQuantitiy
        val barcodeImageView = binding.orderBarcode

        fun bind(orderProduct: OrderProductWithDetails) {
            try {
                productName.text = orderProduct.product.pack.name
                productSum.text = "${orderProduct.product.packPrice!!.price / 100} BYN"
                productSum.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                cartQuantity.text = "x${orderProduct.orderProduct!!.count}"
                productSumBonus.text =
                    "${(orderProduct.product.packPrice!!.price - orderProduct.product.packPrice!!.bonus) / 100} BYN"
                productType.text =
                    "for ${orderProduct.product.pack.quant} ${orderProduct.product.unit!!.name}"

                cartSum.text =
                    "${(orderProduct.product.packPrice!!.price * orderProduct.orderProduct!!.count) / 100} BYN"
                cartSumBonus.text =
                    "${((orderProduct.product.packPrice!!.price - orderProduct.product.packPrice!!.bonus) * orderProduct.orderProduct!!.count) / 100} BYN"
                cartType.text =
                    "for ${orderProduct.product.pack.quant * orderProduct.orderProduct!!.count} ${orderProduct.product.unit!!.name}"
                displayBarcode(orderProduct.product.barcode!!.body, barcodeImageView)
            } catch (e: Exception) {

            }
        }

        fun displayBarcode(text: String, imageView: ImageView) {
            try {
                val bitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, 600, 300)
                val bitmap: Bitmap = BarcodeEncoder().createBitmap(bitMatrix)
                imageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}