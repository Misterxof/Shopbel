package com.misterioes.shopbel.ui.dialog

import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.misterioes.shopbel.data.entity.CartProduct
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.databinding.AddToCartDialogBinding

class AddToCartDialog: DialogFragment() {
    private var _binding: AddToCartDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var product: ProductWithDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddToCartDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            product = it.getSerializable(ARG_PRODUCT) as ProductWithDetails
        }
        val productName = binding.productName
        val productSum = binding.productSum
        val productSumBonus = binding.productSumBonus
        val productType = binding.productType
        val quantityEditText = binding.cartQuantity
        val cartSum = binding.cartSum
        val cartSumBonus = binding.cartSumBonus
        val cartType = binding.cartType

        productName.text = product.pack.name
        productSum.text = "${product.packPrice!!.price / 100} BYN"
        productSum.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        productSumBonus.text =
            "${(product.packPrice!!.price - product.packPrice!!.bonus) / 100} BYN"
        productType.text = "for ${product.pack.quant} ${product.unit!!.name}"

        cartSum.text = "${(product.packPrice!!.price) / 100} BYN"
        cartSumBonus.text =
            "${((product.packPrice!!.price - product.packPrice!!.bonus)) / 100} BYN"
        cartType.text = "for ${product.pack.quant} ${product.unit!!.name}"

        quantityEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val quantityText = p0?.toString()
                if (quantityText != null && quantityText.isNotBlank()) {
                    val quantity = quantityText.toInt()
                    cartSum.text = "${(product.packPrice!!.price * quantity) / 100} BYN"
                    cartSumBonus.text =
                        "${((product.packPrice!!.price - product.packPrice!!.bonus) * quantity) / 100} BYN"
                    cartType.text = "for ${product.pack.quant * quantity} ${product.unit!!.name}"
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        val addButton = binding.cartAddButton
        addButton.setOnClickListener {
            val result = Bundle()

            result.putInt(ARG_QUANTITY, binding.cartQuantity.text.toString().toInt())
            parentFragmentManager.setFragmentResult(RESULT, result)
            dismiss()
        }
    }

    companion object {
        const val RESULT = "CartProduct"
        const val ARG_QUANTITY = "Quantity"
        const val ARG_PRODUCT = "Product"

        fun create(product: ProductWithDetails): AddToCartDialog {
            val dialog = AddToCartDialog()
            val bundle = Bundle()
            bundle.putSerializable(ARG_PRODUCT, product)
            dialog.arguments = bundle
            return dialog
        }
    }
}