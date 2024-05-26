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
    const val SECRET_KEY = "FLWSECK_TEST-7d67c226a7c62a0abd42b255bff47539-X"
    const val ENCRYPTION_KEY = "FLWSECK_TESTa913d67cfde6"
    const val PUBLIC_KEY = "FLWPUBK_TEST-577e0ecead81b6a25d20a78787737277-X"

    fun Double.toCurrency(): String {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "NG"))
        return format.format(this)
    }

}