package com.example.ordermanager.model

data class Message(
    var messageId: String="",
    var messageSenderName: String? = null,
    var messageImage: String="",
    var lastMessage: String? =null,
    var lastMessageTime: String="",
)
