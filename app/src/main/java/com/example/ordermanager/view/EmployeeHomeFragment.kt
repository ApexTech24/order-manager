package com.example.ordermanager.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ordermanager.adapter.EmployeeOrderListAdapter
import com.example.ordermanager.databinding.FragmentEmployeeHomeBinding
import com.example.ordermanager.viewModel.OrderViewModel
import com.example.ordermanager.viewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth


class EmployeeHomeFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeHomeBinding
    private val viewModel : OrderViewModel by activityViewModels()
    private val userViewModel : UserViewModel by activityViewModels()
    var name:String="rana"
    var deliveryManCheck: Boolean =true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentEmployeeHomeBinding.inflate(inflater,container,false)

        userViewModel.getUserById().observe(viewLifecycleOwner){
            name=it.userName
        }


       // val adapter = EmployeeOrderListAdapter()
        val adapter = EmployeeOrderListAdapter {
            val id=it
            if (id != null){
                Toast.makeText(context, ""+ name, Toast.LENGTH_SHORT).show()
                //viewModel.getOrderById(id)
                viewModel.updateOrderById(id,name,deliveryManCheck)
            }

        }
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.reverseLayout=true
        layoutManager.stackFromEnd=true
        binding.employeeOrderRV.layoutManager = layoutManager
        binding.employeeOrderRV.adapter = adapter

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

        return binding.root
    }

}