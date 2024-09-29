package com.example.ordermanager.model


data class Order(
    var id: String = "",
    var orderNo :String ="",
    var productName: String ="",
    var quantity: String="",
    var orderDate: String="",
    var deliveryDate: String="",
    var deliveryMan: String="",
    var deliveryManCheck: Boolean=false,
    var orderImage: String?=null

)
