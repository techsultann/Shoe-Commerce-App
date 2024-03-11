package com.panther.shoeapp.utils

import com.google.android.gms.wallet.WalletConstants
import java.text.NumberFormat
import java.util.Currency
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

    val CURRENCY = Currency.getInstance("NGN")
    val localeCurrency = NumberFormat.getCurrencyInstance(Locale("NGN"))

}