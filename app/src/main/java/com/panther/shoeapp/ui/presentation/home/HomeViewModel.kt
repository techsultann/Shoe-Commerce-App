package com.panther.shoeapp.ui.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _displayName = MutableStateFlow<String>("")
    val displayName : StateFlow<String> = _displayName

    init {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                try {

                    _displayName.value = currentUser.displayName.toString()
                } catch (e: Exception) {
                    // Handle errors gracefully, e.g., log the error or display a message
                    Log.w("HomeViewModel", "Error retrieving display name: $e")
                }
            }
        }
    }
}