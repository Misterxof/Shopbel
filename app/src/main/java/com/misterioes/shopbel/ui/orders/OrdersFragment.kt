package com.misterioes.shopbel.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.misterioes.shopbel.data.entity.Order
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.databinding.FragmentCartBinding
import com.misterioes.shopbel.domain.model.Status
import com.misterioes.shopbel.ui.cart.CartAdapter
import com.misterioes.shopbel.ui.cart.CartViewModel
import com.misterioes.shopbel.ui.dialog.AddToCartDialog
import com.misterioes.shopbel.ui.dialog.AddToCartDialog.Companion.ARG_PRODUCT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrdersFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val adapter: OrderAdapter by lazy { OrderAdapter() }
    private val ordersViewModel: OrdersViewModel by viewModels()
    private lateinit var cartProductsRecycleView: RecyclerView
    private lateinit var order: Order

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            order = it.getSerializable(ARG_ORDER) as Order
        }

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val orderSumBonusTextView= binding.orderSumBonus
        val orderCountTextView = binding.orderCount
        cartProductsRecycleView = binding.cartProductsRecycleView
        val orderButton = binding.orderButton
        orderButton.visibility = INVISIBLE
        cartProductsRecycleView.layoutManager = LinearLayoutManager(context)
        cartProductsRecycleView.adapter = adapter

        ordersViewModel.price.observe(viewLifecycleOwner) {
            orderSumBonusTextView.text = it.toString()
        }
        ordersViewModel.count.observe(viewLifecycleOwner) {
            orderCountTextView.text = it.toString()
        }

        initOrderProducts()
        ordersViewModel.loadProducts(order)

        return root
    }

    fun initOrderProducts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ordersViewModel.products.collect {
                    adapter.setOrderProducts(it)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.e("ProductsFragment", "onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("ProductsFragment", "onDestroyView")
        ordersViewModel.cancelAllOperations()
        viewModelStore.clear()
        _binding = null
    }

    companion object {
        const val ARG_ORDER = "Order"

        fun create(order: Order): Bundle {
            val bundle = Bundle()
            bundle.putSerializable(ARG_ORDER, order)
            return bundle
        }
    }
}