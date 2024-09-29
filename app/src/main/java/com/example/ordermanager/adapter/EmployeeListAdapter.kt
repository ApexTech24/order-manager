package com.example.ordermanager.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ordermanager.databinding.EmployeeListRowItemBinding
import com.example.ordermanager.model.User

class EmployeeListAdapter(val callback: (String) -> Unit) :ListAdapter<User,EmployeeListAdapter.EmployeeListViewHolder>(EmployeeListDiffCallBack()) {
    class EmployeeListViewHolder(
        val binding: EmployeeListRowItemBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind (user: User){
            binding.user=user
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListViewHolder {
        val binding = EmployeeListRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeListViewHolder, position: Int) {
        //val employeeList : User = getItem(position)
        val employeeList = getItem(position)
        holder.bind(employeeList)
        holder.binding.employeeListItem.setOnClickListener {
            callback(employeeList.uid)
            //holder.bind(employeeList)
        }
    }
}

class EmployeeListDiffCallBack : DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.uid==newItem.uid
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem==newItem
    }
}