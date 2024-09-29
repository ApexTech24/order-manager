package com.example.ordermanager.viewModel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ordermanager.model.Order
import com.example.ordermanager.model.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class OrderViewModel : ViewModel() {

    private val collectionOrder="Order"
    private val collectionUser="Users"
    private val db = FirebaseFirestore.getInstance()

    val errMessLD : MutableLiveData<String> = MutableLiveData()


    fun insertOrder(order: Order, callback: () -> Unit){
        val docRef= db.collection(collectionOrder).document()
        order.id= docRef.id
        docRef.set(order)
            .addOnSuccessListener {
                callback()
            }
            .addOnFailureListener {
                errMessLD.value= "Could not Data add!!"
            }
    }

    fun updateOrder(order: Order, callback: () -> Unit){
        val docRef= db.collection(collectionOrder).document()
        order.id= docRef.id
        docRef.set(order)
            .addOnSuccessListener {
                callback()
            }
            .addOnFailureListener {
                errMessLD.value= "Could not Data add!!"
            }
    }

    fun getAllOrder() : LiveData<List<Order>>{

        val orderListLD : MutableLiveData<List<Order>> = MutableLiveData()
        db.collection(collectionOrder).addSnapshotListener { value, error ->
            if (error !=null){
                errMessLD.value=error.localizedMessage
                return@addSnapshotListener
            }
            val temOrderList = mutableListOf<Order>()
            for (doc in value!!.documents){
                temOrderList.add(doc.toObject(Order::class.java)!!)

            }
            orderListLD.value=temOrderList
        }

        return orderListLD
    }

    fun getOrderById(id: String) : LiveData<Order>{

        val orderLD : MutableLiveData<Order> = MutableLiveData()
        db.collection(collectionOrder).document(id).addSnapshotListener { value, error ->
            if (error !=null){
                errMessLD.value=error.localizedMessage
                return@addSnapshotListener
            }

            orderLD.value= value!!.toObject(Order::class.java)
        }

        return orderLD
    }

    fun uploadImage(bitmap: Bitmap, callback: (String) -> Unit) {
        val photoRef= FirebaseStorage.getInstance().reference
            .child("images/${System.currentTimeMillis()}")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,75,baos)
        val data: ByteArray = baos.toByteArray()
        val uploadTask = photoRef.putBytes(data)
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful){
                val downloadUrl = task.result.toString()
                callback(downloadUrl)
            }else{
                //
            }
        }
    }


    //Employee
    fun getEmployeeById(id: String) : LiveData<User>{

        val employeeLD : MutableLiveData<User> = MutableLiveData()
        db.collection(collectionUser).document(id).addSnapshotListener { value, error ->
            if (error !=null){
                errMessLD.value=error.localizedMessage
                return@addSnapshotListener
            }

            employeeLD.value= value!!.toObject(User::class.java)
        }

        return employeeLD
    }

    fun updateOrderById(id: String, name: String, deliveryManCheck: Boolean) {
        val docRef=db.collection(collectionOrder).document(id)
        docRef.update("deliveryMan",name,"deliveryManCheck",deliveryManCheck)
            .addOnSuccessListener {
                //progressDialog
            }
            .addOnFailureListener {
                errMessLD.value=it.localizedMessage
            }
    }

}
