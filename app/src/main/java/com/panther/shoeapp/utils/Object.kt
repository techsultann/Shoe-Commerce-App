package com.panther.shoeapp.utils

import java.util.regex.Pattern

object Object {

    const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"
    fun isNamePatternCorrect(fullName: String): Boolean {
        return fullName.matches(Regex("^[A-Za-z ]+\$"))
    }

    fun isEmailValid(email: String): Boolean {
        return Pattern.matches(EMAIL_VALIDATION_REGEX, email)
    }

}