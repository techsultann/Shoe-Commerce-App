package com.panther.shoeapp.models

data class Review(
    val id: String? = null,
    val productId: String? = null,
    val userId: String? = null,
    val rating: Float? = null,
    val userReview: String? = null,
    val userDisplayName: String? = null
)

