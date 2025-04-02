package com.misterioes.shopbel.ui.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.misterioes.shopbel.R
import com.misterioes.shopbel.data.entity.embedded.CartProductWithDetails
import com.misterioes.shopbel.databinding.CartProductListItemBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartHolder>() {
    private var cartProducts: List<CartProductWithDetails> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding =
            CartProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartProducts.size
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        if (position !in cartProducts.indices) return
        holder.bind(cartProducts[position])
    }

    fun setCartProducts(list: List<CartProductWithDetails>) {
        cartProducts = list.toList()
        notifyDataSetChanged()
    }

    fun clear() {
        val itemCount = cartProducts.size
        cartProducts = emptyList()
        notifyItemRangeRemoved(0, itemCount)
    }

    inner class CartHolder(binding: CartProductListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val productName = binding.cartProductName
        val productSum = binding.cartProductSum
        val productSumBonus = binding.cartProductSumBonus
        val productType = binding.cartProductType
        val cartSum = binding.cartProductTotalSum
        val cartSumBonus = binding.cartProductTotalSumBonus
        val cartType = binding.cartProductTotalType
        val cartQuantity = binding.cartProductQuantitiy

        fun bind(cartProduct: CartProductWithDetails) {
            try {
                productName.text = cartProduct.product.pack.name
                productSum.text = itemView.context.getString(
                    R.string.product_sum,
                    cartProduct.product.packPrice!!.price / 100
                )
                productSum.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                cartQuantity.text = "x${cartProduct.cartProduct!!.count}"
                productSumBonus.text = itemView.context.getString(
                    R.string.product_sum,
                    (cartProduct.product.packPrice.price - cartProduct.product.packPrice.bonus) / 100
                )
                productType.text = itemView.context.getString(
                    R.string.product_type,
                    cartProduct.product.pack.quant,
                    cartProduct.product.unit!!.name
                )

                cartSum.text =
                    itemView.context.getString(
                        R.string.product_sum,
                        (cartProduct.product.packPrice.price * cartProduct.cartProduct.count) / 100
                    )
                cartSum.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                cartSumBonus.text =
                    itemView.context.getString(
                        R.string.product_sum,
                        ((cartProduct.product.packPrice.price - cartProduct.product.packPrice.bonus) * cartProduct.cartProduct.count) / 100
                    )
                cartType.text =
                    itemView.context.getString(
                        R.string.product_type,
                        cartProduct.product.pack.quant * cartProduct.cartProduct.count,
                        cartProduct.product.unit.name
                    )
            } catch (e: Exception) {

            }
        }
    }
}