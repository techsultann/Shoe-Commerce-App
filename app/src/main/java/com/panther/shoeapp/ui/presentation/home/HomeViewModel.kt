package com.panther.shoeapp.ui.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.Category
import com.panther.shoeapp.models.Shoe
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
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
                   // _allShoes.value = allShoes
                }
                .addOnFailureListener{ exception ->
                    Log.e("ALL SHOE", exception.message.toString())
                }
        }
    }


}