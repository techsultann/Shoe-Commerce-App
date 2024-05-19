package com.panther.shoeapp.models.api_response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentRequest(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("customer")
    val customer: Customer,
//    @SerializedName("customizations")
//    val customizations: Customizations,
//    @SerializedName("meta")
//    val meta: Meta,
    @SerializedName("redirect_url")
    val redirectUrl: String,
    @SerializedName("tx_ref")
    val txRef: String
) : Parcelable