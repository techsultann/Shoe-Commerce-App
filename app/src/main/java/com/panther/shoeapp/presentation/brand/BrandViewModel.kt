package com.panther.shoeapp.presentation.brand

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.Shoe
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrandViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _allShoes = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val allShoes : StateFlow<Resource<List<Shoe>>> = _allShoes
    private val _nike = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val nike : StateFlow<Resource<List<Shoe>>> = _nike
    private val _adidas = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val adidas : StateFlow<Resource<List<Shoe>>> = _adidas
    private val _puma = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val puma : StateFlow<Resource<List<Shoe>>> = _puma

    init {

        getAllShoes()
        getNikeShoes()
        getPumaShoes()
        getAdidasShoes()
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

    private fun getNikeShoes() {

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
                        Log.e("NIKE SHOE", exception.message.toString())
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
                        Log.e("ADIDAS SHOE", exception.message.toString())
                    }
            }catch (e: Exception) {
                Log.d("GET ADIDAS SHOES", "${e.message}")
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
                        Log.e("PUMA SHOE", exception.message.toString())
                    }
            }catch (e: Exception) {
                Log.d("GET PUMA SHOES", "${e.message}")
            }

        }
    }

}