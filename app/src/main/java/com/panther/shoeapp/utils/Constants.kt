package com.panther.shoeapp.utils

import java.text.NumberFormat
import java.util.Locale
import java.util.regex.Pattern

object Constants {

    const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"
    fun isNamePatternCorrect(fullName: String): Boolean {
        return fullName.matches(Regex("^[A-Za-z ]+\$"))
    }

    fun isEmailValid(email: String): Boolean {
        return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
    }

    //const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST

    const val COUNTRY_CODE = "NG"

    const val CURRENCY_CODE = "NGN"

    val SHIPPING_SUPPORTED_COUNTRIES = listOf("NG")

    const val BASE_URL = "https://api.flutterwave.com/"
    const val SECRET_KEY = "FLWSECK_TEST-5652673dc7aadf629afa2aa5dc312404-X"
   // const val ENCRYPTION_KEY = "FLWSECK_TEST28878fa86f89"
   // const val PUBLIC_KEY = "FLWPUBK_TEST-9fcdec09a4b963ae3e5c5d36a5465d29-X"

    fun Double.toCurrency(): String {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "NG"))
        return format.format(this)
    }

}