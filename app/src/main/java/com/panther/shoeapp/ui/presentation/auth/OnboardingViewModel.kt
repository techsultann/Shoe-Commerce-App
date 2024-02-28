package com.panther.shoeapp.ui.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.User
import com.panther.shoeapp.repository.RepositoryImpl
import com.panther.shoeapp.utils.Object
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

    private val _signupResult = MutableStateFlow<Resource<User>>(Resource.Loading())
    val signupResult: StateFlow<Resource<User>> = _signupResult
    private val _loginState = MutableStateFlow<Resource<User>>(Resource.Loading())
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
    return Pattern.matches(Object.EMAIL_VALIDATION_REGEX, email)
}

fun isNamePatternCorrect(fullName: String): Boolean {
    return fullName.matches(Regex("^[A-Za-z ]+\$"))
}
