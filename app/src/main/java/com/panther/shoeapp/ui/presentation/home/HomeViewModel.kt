package com.panther.shoeapp.ui.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
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
    private val _nike = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val nike : StateFlow<Resource<List<Shoe>>> = _nike
    private val _adidas = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val adidas : StateFlow<Resource<List<Shoe>>> = _adidas
    private val _puma = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val puma : StateFlow<Resource<List<Shoe>>> = _puma
    private val _itemCount = MutableStateFlow(0)
    val itemCount = _itemCount


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

        getAllShoes()
        getNikeShoes()
        getAdidasShoes()
        getPumaShoes()
    }

    private fun getAllShoes() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _allShoes.value = Resource.Loading()
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
                _allShoes.value = Resource.Loading()
                Log.d("GET ALL SHOES", "${e.message}")
            }

        }
    }

    fun getNikeShoes() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _nike.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .whereEqualTo("brand", "Nike")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val nikeShoes = mutableListOf<Shoe>()

                        for (document in querySnapshot){
                            val shoe = document.toObject(Shoe::class.java)
                            nikeShoes.add(shoe)
                        }
                        _nike.value = Resource.Success(nikeShoes)

                    }
                    .addOnFailureListener{ exception ->
                        _nike.value = Resource.Error(exception.message)
                        Log.e("ALL SHOE", exception.message.toString())
                    }
            }catch (e: Exception) {
                Log.d("GET NIKE SHOES", "${e.message}")
            }

        }
    }

    fun getAdidasShoes() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _adidas.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .whereEqualTo("brand", "Adidas")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val adidasShoes = mutableListOf<Shoe>()

                        for (document in querySnapshot){
                            val shoe = document.toObject(Shoe::class.java)
                            adidasShoes.add(shoe)
                        }
                        _adidas.value = Resource.Success(adidasShoes)

                    }
                    .addOnFailureListener{ exception ->
                        _adidas.value = Resource.Error(exception.localizedMessage)
                        Log.e("ALL SHOE", exception.message.toString())
                    }
            }catch (e: Exception) {
                Log.d("GET NIKE SHOES", "${e.message}")
            }

        }
    }

    private fun getPumaShoes() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _puma.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .whereEqualTo("brand", "Puma")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val pumaShoes = mutableListOf<Shoe>()

                        for (document in querySnapshot){
                            val shoe = document.toObject(Shoe::class.java)
                            pumaShoes.add(shoe)
                        }
                        _puma.value = Resource.Success(pumaShoes)

                    }
                    .addOnFailureListener{ exception ->
                        _puma.value = Resource.Error(exception.localizedMessage)
                        Log.e("ALL SHOE", exception.message.toString())
                    }
            }catch (e: Exception) {
                Log.d("GET NIKE SHOES", "${e.message}")
            }

        }
    }

    fun getItemCount() {
        viewModelScope.launch(Dispatchers.IO) {

            val countQuery = fireStore.collection("Shoes")
                .count()

            countQuery.get(AggregateSource.SERVER).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Count fetched successfully
                    _itemCount.value = (task.result?.count ?: 0).toInt()
                    val snapshot = task.result
                    Log.d("ITEM COUNT", "Number Of Item: ${snapshot.count}")
                } else {
                    Log.d("ITEM COUNT", "Count failed: ", task.exception)
                    _itemCount.value = 0
                }
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