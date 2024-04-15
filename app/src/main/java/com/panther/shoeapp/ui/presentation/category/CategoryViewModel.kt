package com.panther.shoeapp.ui.presentation.category

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
class CategoryViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _allShoes = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val allShoes : StateFlow<Resource<List<Shoe>>> = _allShoes
    private val _menShoes = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val menShoes : StateFlow<Resource<List<Shoe>>> = _menShoes
    private val _womenShoes = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val womenShoes : StateFlow<Resource<List<Shoe>>> = _womenShoes
    private val _kidsShoes = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val kidsShoes : StateFlow<Resource<List<Shoe>>> = _kidsShoes

    init {

        getAllShoes()
        getMenShoes()
        getWomenShoes()
        getKidShoes()
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

    private fun getMenShoes() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _menShoes.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .whereEqualTo("category", "Men")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val menShoes = mutableListOf<Shoe>()

                        for (document in querySnapshot){
                            val shoe = document.toObject(Shoe::class.java)
                            menShoes.add(shoe)
                        }
                        _menShoes.value = Resource.Success(menShoes)

                    }
                    .addOnFailureListener{ exception ->
                        _menShoes.value = Resource.Error(exception.message)
                        Log.e("MEN SHOE", exception.message.toString())
                    }
            }catch (e: Exception) {
                Log.d("GET MEN SHOES", "${e.message}")
            }

        }
    }

    private fun getWomenShoes() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _womenShoes.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .whereEqualTo("category", "Women")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val womenShoes = mutableListOf<Shoe>()

                        for (document in querySnapshot){
                            val shoe = document.toObject(Shoe::class.java)
                            womenShoes.add(shoe)
                        }
                        _womenShoes.value = Resource.Success(womenShoes)

                    }
                    .addOnFailureListener{ exception ->
                        _womenShoes.value = Resource.Error(exception.message)
                        Log.e("WOMEN SHOE", exception.message.toString())
                    }
            }catch (e: Exception) {
                Log.d("GET WOMEN SHOES", "${e.message}")
            }

        }
    }

    private fun getKidShoes() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _kidsShoes.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .whereEqualTo("category", "Men")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val kidShoes = mutableListOf<Shoe>()

                        for (document in querySnapshot){
                            val shoe = document.toObject(Shoe::class.java)
                            kidShoes.add(shoe)
                        }
                        _kidsShoes.value = Resource.Success(kidShoes)

                    }
                    .addOnFailureListener { exception ->
                        _kidsShoes.value = Resource.Error(exception.message)
                        Log.e("KID SHOE", exception.message.toString())
                    }
            }catch (e: Exception) {
                Log.d("GET KID SHOES", "${e.message}")
            }

        }
    }


}