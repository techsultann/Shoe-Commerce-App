package com.panther.shoeapp.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val orderId: String? = "",
    val userId: String? = null,
    val totalPrice: Double? = null,
    val status: String? = null,
    val timeStamp: String? = null,
    val address: String? = null,
    val cartItem: Map<String, CartItem>? = null,
    val name: String? = null,
    val phoneNumber: String? = null,
)