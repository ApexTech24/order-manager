package com.example.ordermanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.ordermanager.databinding.EmployeeOrderListRowBinding
import com.example.ordermanager.model.Order

class EmployeeOrderListAdapter(val callback: (String) -> Unit) :
    ListAdapter<Order, EmployeeOrderListAdapter.EmployeeOrderViewHolder>(EmployeeOrderDiffCallback()) {

    class EmployeeOrderViewHolder(
        val binding: EmployeeOrderListRowBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order:Order) {
            binding.order = order

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeOrderViewHolder {
        val binding = EmployeeOrderListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeOrderViewHolder, position: Int) {
        val employeeOrderModel: Order = getItem(position)
        holder.bind(employeeOrderModel)
        holder.binding.deliveryByBTN.setOnClickListener {
            callback(employeeOrderModel.id)
            employeeOrderModel.deliveryManCheck = !employeeOrderModel.deliveryManCheck
            holder.bind(employeeOrderModel)
            //holder.binding.deliveryByBTN.visibility= View.INVISIBLE
        }
    }
}

class EmployeeOrderDiffCallback : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem:Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

}
