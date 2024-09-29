package com.example.ordermanager.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ordermanager.MainActivity
import com.example.ordermanager.R
import com.example.ordermanager.adapter.OrderAdapter
import com.example.ordermanager.databinding.FragmentHomeBinding
import com.example.ordermanager.viewModel.OrderViewModel
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel:OrderViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)

        val adapter = OrderAdapter{
            val orderItemId=it
            val bundle = bundleOf("orderItemId" to orderItemId)
            findNavController().navigate(R.id.action_homeFragment_to_updateOrderFragment)

        }
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.reverseLayout=true
        layoutManager.stackFromEnd=true
        binding.orderListRecyclerView.layoutManager = layoutManager
            binding.orderListRecyclerView.adapter = adapter

        viewModel.getAllOrder().observe(viewLifecycleOwner){
            if (it.isEmpty()){
                Toast.makeText(context, "No Data Found!!!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Successful Data Found!", Toast.LENGTH_SHORT).show()
            }
            adapter.submitList(it)
        }

        viewModel.errMessLD.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        onClickListener()
        return binding.root
    }


    private fun onClickListener() {
        binding.orderNowBTN.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_orderFragment)
        }
    }

}