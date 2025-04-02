package com.misterioes.shopbel.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.misterioes.shopbel.data.entity.Order
import com.misterioes.shopbel.databinding.HistoryListItemBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ProfileAdapter @AssistedInject constructor(
    @Assisted val onOrderClick: (order: Order) -> Unit
) : RecyclerView.Adapter<ProfileAdapter.OrderHolder>() {
    private val orders = mutableListOf<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val binding =
            HistoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderHolder(binding, onOrderClick)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        holder.bind(orders[position])
    }

    fun setOrders(list: List<Order>) {
        list.forEach {
            if (!orders.contains(it)) {
                orders.add(it)
                notifyDataSetChanged()
            }
        }
    }

    inner class OrderHolder(
        binding: HistoryListItemBinding,
        val onOrderClick: (order: Order) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var order: Order
        val date = binding.orderDate

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(order: Order) {
            this.order = order
            date.text = order.date.toString()
        }

        override fun onClick(p0: View?) {
            onOrderClick(order)
        }
    }
}