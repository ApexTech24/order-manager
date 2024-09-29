package com.example.ordermanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ordermanager.R
import com.example.ordermanager.databinding.MessageRowItemBinding
import com.example.ordermanager.databinding.ReceiveMessageRowItemBinding
import com.example.ordermanager.model.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        val ITEM_RECEIVED = 1
        val ITEM_SEND = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            val view:View = LayoutInflater.from(context).inflate(R.layout.receive_message_row_item,parent,false)
            return MessageReceivedViewHolder(view)
        }
        else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.message_row_item,parent,false)
            return MessageSendViewHolder(view)

        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]
        if (holder.javaClass == MessageSendViewHolder::class.java){

            //send message view holder

            val viewHolder = holder as MessageSendViewHolder
            holder.sendMessage.text = currentMessage.lastMessage
            holder.sendName.text = currentMessage.messageSenderName
            holder.sendTime.text = currentMessage.lastMessageTime
        }else{
            //received message view holder
            val viewHolder = holder as MessageReceivedViewHolder
            holder.receivedName.text= currentMessage.messageSenderName
            holder.receivedMessage.text= currentMessage.lastMessage
            holder.receivedTime.text= currentMessage.lastMessageTime
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.messageId)){
            return ITEM_SEND
        }else{
            return ITEM_RECEIVED
        }
    }

    /*private val binding:MessageRowItemBinding*/
    class MessageSendViewHolder(itemView:View):
        RecyclerView.ViewHolder(itemView){

            val sendName= itemView.findViewById<TextView>(R.id.userNameTV)
            val sendMessage= itemView.findViewById<TextView>(R.id.send_messageTV)
            val sendTime= itemView.findViewById<TextView>(R.id.messageTimeTV)
        /*fun bind(message: Message) {
            binding.message = message

        }*/

    }

    /*private val binding:ReceiveMessageRowItemBinding*/
    class MessageReceivedViewHolder(itemView:View):
        RecyclerView.ViewHolder(itemView){

        val receivedName= itemView.findViewById<TextView>(R.id.userNameTV)
        val receivedMessage= itemView.findViewById<TextView>(R.id.received_messageTV)
        val receivedTime= itemView.findViewById<TextView>(R.id.messageTimeTV)
        /*fun bind(message: Message) {
            binding.message = message

        }*/

    }

}
