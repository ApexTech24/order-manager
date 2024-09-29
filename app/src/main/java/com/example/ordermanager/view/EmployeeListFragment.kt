package com.example.ordermanager.view

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
import com.example.ordermanager.R
import com.example.ordermanager.adapter.EmployeeListAdapter
import com.example.ordermanager.adapter.EmployeeOrderListAdapter
import com.example.ordermanager.databinding.FragmentEmployeeListBinding
import com.example.ordermanager.viewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth


class EmployeeListFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeListBinding
    private val userViewModel : UserViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentEmployeeListBinding.inflate(inflater,container,false)

        val adapter = EmployeeListAdapter {id ->
            val bundle= bundleOf("uid" to  id)
            Toast.makeText(context, id, Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_employeeListFragment_to_chattingFragment,bundle)
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.reverseLayout=true
        layoutManager.stackFromEnd=true
        binding.employeeListRV.layoutManager = layoutManager
        binding.employeeListRV.adapter = adapter

        userViewModel.getAllEmployee().observe(viewLifecycleOwner){
            if (it.isEmpty()){
                Toast.makeText(context, "No Data Found!!!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Successful Data Found!", Toast.LENGTH_SHORT).show()
            }
            adapter.submitList(it)

        }

        userViewModel.errMessLD.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}