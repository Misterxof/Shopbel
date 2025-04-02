package com.misterioes.shopbel.ui.products

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.misterioes.shopbel.data.entity.embedded.ProductWithDetails
import com.misterioes.shopbel.databinding.FragmentProductsBinding
import com.misterioes.shopbel.di.ProductAdapterAssistedFactory
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
    private lateinit var productsViewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productsViewModel =
            ViewModelProvider(this).get(ProductsViewModel::class.java)

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val productsRecyclerView = binding.productsRecycleView
        adapter = productAdapterAssistedFactory.create { product ->
            createAddToCartDialog(product)
        }
        productsRecyclerView.layoutManager = LinearLayoutManager(context)
        productsRecyclerView.adapter = adapter
        initProducts()

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
                    Log.e("*************", it.toString())
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
                            }
                        }
                    }
                }
            }
        }.show(this@ProductsFragment.parentFragmentManager, AddToCartDialog.RESULT)
    }
}