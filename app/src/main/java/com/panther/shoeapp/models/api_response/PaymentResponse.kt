package com.panther.shoeapp.models.api_response


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)