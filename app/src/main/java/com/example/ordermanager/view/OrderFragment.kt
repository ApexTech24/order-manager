package com.example.ordermanager.view

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.ordermanager.R
import com.example.ordermanager.databinding.FragmentOrderBinding
import com.example.ordermanager.model.Message
import com.example.ordermanager.model.Order
import com.example.ordermanager.viewModel.OrderViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage


class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding
    private val viewModel : OrderViewModel by activityViewModels()

    private var db=Firebase.firestore
    private var storeRef=Firebase.storage
    //private lateinit var uri:Uri
    private var imageUrl: String? = null
    private var bitmap: Bitmap?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentOrderBinding.inflate(inflater, container, false)
        onClickListener()

        storeRef=FirebaseStorage.getInstance()
        return binding.root
    }

    private fun onClickListener() {
        binding.addImgBTN.setOnClickListener {
            //imageGallery.launch("image/*")
            dispatchTakeImage()

        }

        binding.orderSubmitBTN.setOnClickListener {

            val orderNo= binding.orderNoET.text.trim().toString()
            val productName= binding.productNameET.text.trim().toString()
            val quantity= binding.quantityET.text.trim().toString()
            val deliveryDate= binding.deliveryDateET.text.trim().toString()

            viewModel.uploadImage(bitmap!!){downloadUrl ->
                imageUrl = downloadUrl

                val mOrder = Order(
                    orderNo=orderNo,
                    productName = productName,
                    quantity = quantity,
                    deliveryDate = deliveryDate,
                    orderImage = imageUrl

                )
                viewModel.insertOrder(mOrder){
                    findNavController().popBackStack()
                }
            }

        }
    }
    val resultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

        if (it.resultCode == Activity.RESULT_OK){
            bitmap= it.data?.extras?.get("data") as Bitmap
            binding.orderImgView.setImageBitmap(bitmap)
            imageUrl= it.toString()
        }

    }

    private fun dispatchTakeImage() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            resultLauncher.launch(takePicture)
        }catch (e: ActivityNotFoundException){
            //
        }
    }

}