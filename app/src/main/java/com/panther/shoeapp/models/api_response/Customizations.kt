package com.panther.shoeapp.models.api_response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customizations(
    @SerializedName("logo")
    val logo: String,
    @SerializedName("title")
    val title: String
) : Parcelable