package com.panther.shoeapp.models

data class CreditCard(
    val cardType: String? = null,
    val cardName: String? = null,
    val cardNumber: String? = null,
    val expiryDate: String? = null,
    val cvv: String? = null
)
