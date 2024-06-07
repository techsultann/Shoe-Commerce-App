package com.panther.shoeapp.models

import kotlinx.serialization.Serializable


data class CartItem(
    val id: String? = null,
    val name: String? = null,
    val price: Double? = null,
    val image: String? = null,
    var quantity: Int? = null,
)