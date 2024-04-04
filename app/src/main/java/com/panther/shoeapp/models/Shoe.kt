package com.panther.shoeapp.models

data class Shoe(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val images: List<String>? = null,
    val brand: String? = null,
    val sizes: List<String>? = null,
    val colors: List<String>? = null,
    val category: String? = null,
    val featured: Boolean? = null,
    val stock: Int? = null
)

data class CartItem(
    val id: String? = null,
    val name: String? = null,
    val price: Double? = null,
    val image: String? = null,
    var quantity: Int? = null,
)

