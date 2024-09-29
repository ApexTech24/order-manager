package com.example.ordermanager.view

import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ordermanager.R
import com.example.ordermanager.adapter.MessageAdapter
import com.example.ordermanager.databinding.FragmentChattingBinding
import com.example.ordermanager.model.Message
import com.example.ordermanager.model.Order
import com.example.ordermanager.model.User
import com.example.ordermanager.viewModel.MessageViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class ChattingFragment : Fragment() {

    private lateinit var binding: FragmentChattingBinding
    private val viewModel : MessageViewModel by activityViewModels()
    //val user: User = User()

    var uName:String = ""
    var userId: String? = null

    private val collectionMessage="Message"
    private val db = Firebase.firestore
    private lateinit var docRef : DatabaseReference

    val senderUId= FirebaseAuth.getInstance().currentUser?.uid


    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>

    var sederRoom: String?=null
    var receiverRoom: String?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChattingBinding.inflate(inflater,container,false)

        val receiverUid= arguments?.getString("uid")
        if (receiverUid != null){
            Toast.makeText(context, receiverUid, Toast.LENGTH_SHORT).show()
        }
        onClickListener()

        viewModel.getUserById().observe(viewLifecycleOwner){
            uName=it.userName
            userId=it.uid
        }

        docRef= FirebaseDatabase.getInstance().getReference()

        sederRoom=receiverUid + senderUId
        receiverRoom=senderUId + receiverUid

        messageList =ArrayList()
        messageAdapter= MessageAdapter(requireContext(),messageList)


        val layoutManager = LinearLayoutManager(requireActivity())
        /*layoutManager.reverseLayout=true
        layoutManager.stackFromEnd=true*/
        binding.messageRV.layoutManager = layoutManager
        binding.messageRV.adapter = messageAdapter

        docRef.child(collectionMessage).child(sederRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message=postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }

                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        return binding.root
    }

    private fun onClickListener() {

        binding.sendMessageIV.setOnClickListener {
            //val messageName= user.userName

            val message= binding.messageET.text.trim().toString()
            val messageTime= SystemClock.currentThreadTimeMillis().toString()

            val messageObject =Message(senderUId!!,uName,"",message,messageTime)

            docRef.child(collectionMessage).child(sederRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    docRef.child(collectionMessage).child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }

                    /*db.collection(collectionMessage).document(sederRoom.toString())
                .set(messageObject)
                .addOnSuccessListener {
                    db.collection(collectionMessage).document(receiverRoom.toString())
                }
                .addOnFailureListener {

                }*/
            binding.messageET.setText("")

        }

    }


}