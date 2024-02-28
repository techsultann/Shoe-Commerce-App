package com.panther.shoeapp.ui.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.Category
import com.panther.shoeapp.models.Shoe
import com.panther.shoeapp.models.User
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _displayName = MutableStateFlow<String>("")
    val displayName : StateFlow<String> = _displayName
    private val _allShoes = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val allShoes : StateFlow<Resource<List<Shoe>>> = _allShoes
    private val _shoeById = MutableStateFlow<Resource<Shoe>>(Resource.Loading())
    val shoeById: StateFlow<Resource<Shoe>> = _shoeById
    private val _allCategory = MutableStateFlow<Resource<Category>>(Resource.Loading())
    val category : StateFlow<Resource<Category>> = _allCategory

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

    fun getAllShoes() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                fireStore.collection("Shoes")
                    .get()
                    .addOnSuccessListener { result ->
                        val shoeList = mutableListOf<Shoe>()
                        for (document in result) {
                            val shoe = document.toObject(Shoe::class.java)
                            shoeList.add(shoe)
                        }
                        _allShoes.value = Resource.Success(shoeList)
                        Log.d("GET ALL SHOES", "Shoes: $shoeList")
                    }
                    .addOnFailureListener { e ->
                        _allShoes.value = Resource.Error(e.localizedMessage)
                    }
            } catch (e: Exception) {
                Log.d("GET ALL SHOES", "${e.message}")
            }

        }
    }

    fun getShoeById(shoeId: String) {
        fireStore.collection("Shoes")
            .document(shoeId)
            .get()
            .addOnSuccessListener { document ->
                val shoe = document.toObject(Shoe::class.java)
                Log.d("GET ALL SHOES", "Shoes: $shoe")
                _shoeById.value = Resource.Success(shoe!!)
            }
            .addOnFailureListener { e ->
                _shoeById.value = Resource.Error(e.localizedMessage)
            }
    }

    fun getNikeShoes() {

        viewModelScope.launch {
            fireStore.collection("shoes")
                .whereEqualTo("brand", "Nike")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val allShoes = mutableListOf<Shoe>()

                    for (document in querySnapshot.documents){
                        val shoe = document.toObject(Shoe::class.java)
                        if (shoe != null) {
                            allShoes.add(shoe)
                        }

                    }

                }
                .addOnFailureListener{ exception ->
                    Log.e("ALL SHOE", exception.message.toString())
                }
        }
    }

    fun logout(): Resource<User> {
        return try {
            auth.signOut()
            Log.d("TAG", "Logout: SUCCESS")
            Resource.Success(User())
        } catch (e: Exception) {
            Log.e("TAG", "Logout: FAILED")
            Resource.Error("Failed")
        }
    }


}