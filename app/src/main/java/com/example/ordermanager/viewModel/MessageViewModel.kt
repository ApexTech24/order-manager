package com.example.ordermanager.viewModel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ordermanager.model.Message
import com.example.ordermanager.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class MessageViewModel: ViewModel() {

    private val collectionMessage="Message"
    private val collectionUser="Users"
    val fireAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    val errMessLD : MutableLiveData<String> = MutableLiveData()
    val userLD : MutableLiveData<User> = MutableLiveData()

    fun getUser() : LiveData<User> {

        val uid= fireAuth.currentUser?.uid

        val userListLD : MutableLiveData<User> = MutableLiveData()
        val docRef= db.collection(collectionUser).document(uid!!)

        docRef.get().addOnSuccessListener {document ->
            if (document != null){

                //name = document.data!!["userName"].toString()
            }

        }.addOnFailureListener { exception ->
            errMessLD.value=exception.localizedMessage

        }
        return userListLD

    }
    fun getUserById() : LiveData<User> {

        var uId= fireAuth.currentUser!!.uid
        if (uId== fireAuth.currentUser!!.uid){
            db.collection(collectionUser).document(uId).addSnapshotListener { value, error ->
                if (error !=null){
                    errMessLD.value=error.localizedMessage
                    return@addSnapshotListener
                }

                userLD.value= value!!.toObject(User::class.java)
            }
        }

        return userLD
    }


    fun insertMessage(message: Message, callback: () -> Unit){
        val docRef= db.collection(collectionMessage).document()
        message.messageId= docRef.id
        docRef.set(message)
            .addOnSuccessListener {
                callback()
            }
            .addOnFailureListener {
                errMessLD.value= "Could not Data add!!"
            }
    }
}