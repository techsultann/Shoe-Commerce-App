package com.panther.shoeapp.utils

import com.google.android.gms.wallet.WalletConstants
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

    const val PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST

    const val COUNTRY_CODE = "NG"

    const val CURRENCY_CODE = "NGN"

    val SHIPPING_SUPPORTED_COUNTRIES = listOf("NG")

    const val BASE_URL = "https://api.flutterwave.com/"
    const val SECRET_KEY = "FLWSECK_TEST-37d4beac3417e55f60db5da6dce65bbc-X"
    const val ENCRYPTION_KEY = "FLWSECK_TEST0ef8074119a7"
    const val PUBLIC_KEY = "FLWPUBK_TEST-787b427b4f2e85724620951e53ad461e-X"

    fun Double.toCurrency(): String {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "NG"))
        return format.format(this)
    }

}