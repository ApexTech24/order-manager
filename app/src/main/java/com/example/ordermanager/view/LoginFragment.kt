package com.example.ordermanager.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ordermanager.MainActivity
import com.example.ordermanager.R
import com.example.ordermanager.databinding.FragmentLoginBinding
import com.example.ordermanager.viewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(inflater,container,false)

        /*userViewModel.authLD.observe(viewLifecycleOwner){id ->
            if (id== UserViewModel.Auth.AUTHENTICATED){

                findNavController().popBackStack()
                Toast.makeText(context, "login successful", Toast.LENGTH_SHORT).show()
            }
        }*/
        userViewModel.userTypeAuthLD.observe(viewLifecycleOwner){
            if (it !=UserViewModel.USER.ISADMIN){
                val intent = Intent(context, MainActivity2::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
        }
        userViewModel.errMessLD.observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        setOnClickListener()
        return binding.root
    }

    private fun setOnClickListener() {
        binding.loginBTN.setOnClickListener {
            val email= binding.emailET.text.toString()
            val password= binding.passwordET.text.toString()

            userViewModel.adminLogin(email,password)

        }
    }


}