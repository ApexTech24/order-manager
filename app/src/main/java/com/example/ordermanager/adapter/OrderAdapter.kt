package com.example.ordermanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ordermanager.databinding.OrderListRowItemBinding
import com.example.ordermanager.model.Order

class OrderAdapter(val callback: (String) -> Unit) : ListAdapter<Order, OrderAdapter.OrderViewHolder>(OrderDiffCallback()) {

    class OrderViewHolder(
        val binding: OrderListRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.order = order

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderListRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val orderModel = getItem(position)
        holder.bind(orderModel)

        holder.binding.editItemIV.setOnClickListener {
            callback(orderModel.id)
            //holder.bind(orderModel)
        }
    }
}

class OrderDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

}