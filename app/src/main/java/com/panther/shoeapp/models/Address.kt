package com.panther.shoeapp.models

import kotlinx.serialization.Serializable

data class Address(
    val id: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val street: String? = null,
    val apartment: String? = null,
    val city: String? = null,
    val postcode: String? = null,
    val state: String? = null,
    val phoneNumber: String? = null,
)
