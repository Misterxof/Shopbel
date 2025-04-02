package com.misterioes.shopbel.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.misterioes.shopbel.R
import com.misterioes.shopbel.databinding.FragmentProfileBinding
import com.misterioes.shopbel.di.OrderAdapterAssistedFactory
import com.misterioes.shopbel.di.ProductAdapterAssistedFactory
import com.misterioes.shopbel.ui.orders.OrdersFragment
import com.misterioes.shopbel.ui.products.ProductAdapter
import com.misterioes.shopbel.ui.products.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ProfileAdapter
    @Inject
    lateinit var orderAdapterAssistedFactory: OrderAdapterAssistedFactory
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val ordersRecyclerView = binding.historyRecycleView
        adapter = orderAdapterAssistedFactory.create { order ->
            findNavController().navigate(
                R.id.action_navigation_profile_to_navigation_orders,
                bundleOf("Order" to order)
            )
        }
        ordersRecyclerView.layoutManager = LinearLayoutManager(context)
        ordersRecyclerView.adapter = adapter
        initOrders()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initOrders() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.orders.collect {
                    Log.e("*************", it.toString())
                    adapter.setOrders(it)
                }
            }
        }
    }
}