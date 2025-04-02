package com.misterioes.shopbel.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.misterioes.shopbel.databinding.FragmentCartBinding
import com.misterioes.shopbel.domain.model.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val adapter: CartAdapter by lazy { CartAdapter() }
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartProductsRecycleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cartViewModel =
            ViewModelProvider(this).get(CartViewModel::class.java)

        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val orderSumBonusTextView= binding.orderSumBonus
        val orderCountTextView = binding.orderCount
        cartProductsRecycleView = binding.cartProductsRecycleView
        val orderButton = binding.orderButton
        cartProductsRecycleView.layoutManager = LinearLayoutManager(context)
        cartProductsRecycleView.adapter = adapter

        cartViewModel.price.observe(viewLifecycleOwner) {
            orderSumBonusTextView.text = it.toString()
        }
        cartViewModel.count.observe(viewLifecycleOwner) {
            orderCountTextView.text = it.toString() + " products"
        }

        orderButton.setOnClickListener {
            cartViewModel.sendOrder()
        }
        
        initCartProducts()

        return root
    }

    fun initCartProducts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.products.collect {
                    if (it.isEmpty()) {
                        adapter.clear()
                    } else adapter.setCartProducts(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartViewModel.state.collect {
                    when(it) {
                        is Status.Error -> Toast.makeText(context, "Something went wrong! ${it.error}", Toast.LENGTH_LONG).show()
                        is Status.Loading -> {}
                        is Status.Success -> {
                            Toast.makeText(context, "Order is created!", Toast.LENGTH_LONG).show()
                        }
                    }
                    cartViewModel.updateState()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}