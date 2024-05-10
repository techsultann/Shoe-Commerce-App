package com.panther.shoeapp.models.api_response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Meta(
    @SerializedName("consumer_id")
    val consumerId: Int,
    @SerializedName("consumer_mac")
    val consumerMac: String
) : Parcelable