package com.panther.shoeapp.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.Address
import com.panther.shoeapp.models.User
import com.panther.shoeapp.utils.Resource
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
    private val _userData = MutableStateFlow<Resource<User>>(Resource.Loading())
    val userData: StateFlow<Resource<User>> = _userData
    private val _getAddress = MutableStateFlow<Resource<List<Address>>>(Resource.Loading())
    val getAddress: StateFlow<Resource<List<Address>>> = _getAddress

    init {
        displayName()
        userEmail()
        getUserData()
        getAddressList()
    }

    private fun displayName() {
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

    private fun userEmail() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                try {
                    _userEmail.value = currentUser.email.toString()
                    Log.d("PROFILE", "Profile Email: ${currentUser.email}")

                } catch (e: Exception) {
                    // Handle errors gracefully, e.g., log the error or display a message
                    Log.w("HomeViewModel", "Error retrieving user email: $e")
                }
            }
        }
    }

    private fun getUserData() {

        val currentUser = auth.currentUser
        val userId = currentUser!!.uid

        viewModelScope.launch(Dispatchers.IO) {

            try {
                fireStore.collection("users")
                    .document(userId)
                    .get()
                    .addOnSuccessListener { document ->
                        val user = document.toObject(User::class.java)
                        _userData.value = Resource.Success(user!!)
                    }
                    .addOnFailureListener { e ->
                        _userData.value = Resource.Error(e.localizedMessage)
                    }

            } catch (e: Exception) {
                _userData.value = Resource.Error(e.localizedMessage)
            }
        }
    }

    fun updateDisplayName(
        name: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = auth.currentUser
            if (currentUser != null) {

                try {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }
                    currentUser.updateProfile(profileUpdates)
                } catch (e: Exception) {
                    // Handle errors gracefully, e.g., log the error or display a message
                    Log.w("HomeViewModel", "Error retrieving display name: $e")
                }

            }
        }
    }



    fun updatePhoneNumber(
        phoneNumber: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = auth.currentUser
            val userId = currentUser?.uid

            val userData = hashMapOf<String, Any>(
                "phoneNumber" to phoneNumber
            )

            if (currentUser != null) {

                try {
                    fireStore.collection("users")
                        .document(userId!!)
                        .update(userData)
                } catch (e: Exception) {
                    // Handle errors gracefully, e.g., log the error or display a message
                    Log.w("HomeViewModel", "Error retrieving display name: $e")
                }

            }
        }
    }

    private fun getAddressList() {

        viewModelScope.launch(Dispatchers.IO) {

            val userId = auth.currentUser!!.uid
            try {
                _getAddress.value = Resource.Loading()

                fireStore.collection("users")
                    .document(userId)
                    .collection("User Address")
                    .get()
                    .addOnSuccessListener { result ->

                        val addressList = result.toObjects(Address::class.java)
                        _getAddress.value = Resource.Success(addressList)

                        Log.d("GET ALL SHOES", "Shoes: $addressList")
                    }
                    .addOnFailureListener { e ->
                        _getAddress.value = Resource.Error(e.localizedMessage)
                    }
            } catch (e: Exception) {
                _getAddress.value = Resource.Loading()
                Log.d("GET ALL SHOES", "${e.message}")
            }

        }
    }

}