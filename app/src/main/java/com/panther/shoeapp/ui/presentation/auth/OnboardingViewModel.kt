package com.panther.shoeapp.ui.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.panther.shoeapp.repository.Repository
import com.panther.shoeapp.utils.Object
import com.panther.shoeapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

class OnboardingViewModel @Inject constructor (
    private val repo: Repository
) : ViewModel() {

    private val _signupResult = MutableStateFlow<Resource<AuthResult>>(Resource.Loading())
    val signupResult: StateFlow<Resource<AuthResult>> = _signupResult

    fun signUp(
        email: String,
        password: String,
        username: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            if (isEmailValid(email)) {
                _signupResult.value = repo.signUp(email, password, username)

            } else {

                return@launch
            }


        }
    }



    fun Login(email: String, password: String){

        viewModelScope.launch(Dispatchers.IO) {


        }
    }

    fun logOut(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.logout()
        }
    }
}
 private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(Object.EMAIL_VALIDATION_REGEX, email)
}

fun isNamePatternCorrect(fullName: String): Boolean {
    return fullName.matches(Regex("^[A-Za-z ]+\$"))
}

data class SignupState(
    val isLoading: Boolean = false,
    val data: AuthResult? = null,
    val error: String? = null,
)