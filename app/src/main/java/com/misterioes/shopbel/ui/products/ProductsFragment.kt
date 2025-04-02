package com.misterioes.shopbel.ui.products

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.misterioes.shopbel.R
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.databinding.FragmentProductsBinding
import com.misterioes.shopbel.di.ProductAdapterAssistedFactory
import com.misterioes.shopbel.domain.model.User
import com.misterioes.shopbel.domain.model.UserInfo
import com.misterioes.shopbel.ui.dialog.AddToCartDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProductAdapter
    @Inject
    lateinit var productAdapterAssistedFactory: ProductAdapterAssistedFactory
    private val productsViewModel: ProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val productsRecyclerView = binding.productsRecycleView
        adapter = productAdapterAssistedFactory.create { product ->
            createAddToCartDialog(product)
        }
        productsRecyclerView.layoutManager = LinearLayoutManager(context)
        productsRecyclerView.adapter = adapter
        initProducts()
        productsViewModel.loadProductsFromFirestore()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initProducts() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productsViewModel.products.collect {
                    adapter.setProducts(it)
                }
            }
        }
    }

    fun createAddToCartDialog(product: ProductWithDetails) {
        AddToCartDialog.create(product).apply {
            this@ProductsFragment.parentFragmentManager.setFragmentResultListener(
                AddToCartDialog.RESULT,this
            ) { k, b ->
                k.let {
                    when(it) {
                        AddToCartDialog.RESULT -> {
                            val quantity = b.getInt(AddToCartDialog.ARG_QUANTITY)
                            if (quantity > 0 && quantity <= 100) {
                                productsViewModel.saveToCart(product, quantity)
                            } else {
                                Toast.makeText(context, getString(R.string.invalid_quantity), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }.show(this@ProductsFragment.parentFragmentManager, AddToCartDialog.RESULT)
    }
}