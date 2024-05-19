package com.panther.shoeapp.models.api_response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("link")
    val link: String
)