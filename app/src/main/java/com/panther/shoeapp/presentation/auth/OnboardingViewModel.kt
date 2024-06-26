package com.panther.shoeapp.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.User
import com.panther.shoeapp.repository.RepositoryImpl
import com.panther.shoeapp.utils.Constants
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor (
    private val repo: RepositoryImpl,
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _signupResult = MutableStateFlow<Resource<User>>(Resource.Idle())
    val signupResult: StateFlow<Resource<User>> = _signupResult
    private val _loginState = MutableStateFlow<Resource<User>>(Resource.Idle())
    val loginState : StateFlow<Resource<User>> = _loginState

    fun signUp(
        email: String,
        password: String,
        username: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            if (isEmailValid(email)) {
                _signupResult.value = repo.signUp(email, password, username)
                Log.d("SIGNUP TAG", "SIGNUP: ${_signupResult.value.data}")

            } else {
                return@launch
            }


        }
    }

    fun saveUserDataToFiresStore(
        userName: String,
        email: String
    ){
        viewModelScope.launch (Dispatchers.IO) {

            repo.saveUserDataToFiresStore(userName, email)

        }

    }

    fun login(email: String, password: String){

        viewModelScope.launch(Dispatchers.IO) {
            if (isEmailValid(email)) {
                try {
                    val result = repo.login(email, password)
                    _loginState.value = result
                    Log.d("LOGIN TAG", "LOGIN: ${result.message}")
                } catch (e: Exception) {
                    Log.e("LOGIN TAG", "Login: FAILED", e)
                    _loginState.value = Resource.Error("Failed")
                }
                    val login = loginState.value
                    _loginState.value = repo.login(email, password)
                    Log.d("LOGIN TAG", "LOGIN: $login")
            }

        }
    }


    fun logOut(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.logout()
        }
    }
}
 private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(Constants.EMAIL_VALIDATION_REGEX, email)
}

fun isNamePatternCorrect(fullName: String): Boolean {
    return fullName.matches(Regex("^[A-Za-z ]+\$"))
}
