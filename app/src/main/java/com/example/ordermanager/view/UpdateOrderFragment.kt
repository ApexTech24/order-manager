package com.example.ordermanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ordermanager.R
import com.example.ordermanager.databinding.FragmentUpdateOrderBinding
import com.example.ordermanager.viewModel.OrderViewModel

class UpdateOrderFragment : Fragment() {
    private lateinit var binding: FragmentUpdateOrderBinding
    private val viewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUpdateOrderBinding.inflate(inflater,container,false)
        val orderItemId= arguments?.getString("orderItemId","0")
        if (orderItemId != null){
            viewModel.getOrderById(orderItemId)
        }
        return binding.root
    }
}