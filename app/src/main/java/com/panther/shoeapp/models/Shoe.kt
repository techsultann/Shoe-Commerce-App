package com.panther.shoeapp.models

import com.google.firebase.firestore.DocumentReference

data class Shoe(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val brand: String,
    val sizes: List<String>,
    val colors: List<String>,
    val categoryId: DocumentReference,
    val featured: Boolean,
    val stock: Int
)
