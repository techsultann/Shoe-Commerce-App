package com.panther.shoeapp.ui.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _displayName = MutableStateFlow("")
    val displayName : StateFlow<String> = _displayName
    private val _userEmail = MutableStateFlow("")
    val userEmail : StateFlow<String> = _userEmail

//    init {
//        displayName()
//        userEmail()
//    }

    fun displayName() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                try {
                    _displayName.value = currentUser.displayName.toString()
                    Log.d("PROFILE", "Profile Name: ${currentUser.displayName}")
                } catch (e: Exception) {
                    // Handle errors gracefully, e.g., log the error or display a message
                    Log.w("HomeViewModel", "Error retrieving display name: $e")
                }
            }
        }
    }

    fun userEmail() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                try {
                    Log.d("PROFILE", "Profile Email: ${currentUser.email}")
                    _displayName.value = currentUser.email.toString()
                } catch (e: Exception) {
                    // Handle errors gracefully, e.g., log the error or display a message
                    Log.w("HomeViewModel", "Error retrieving user email: $e")
                }
            }
        }
    }
}