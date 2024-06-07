package com.panther.shoeapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.Review
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
    private val _newDeals = MutableStateFlow<Resource<List<Shoe>>>(Resource.Loading())
    val newDeals : StateFlow<Resource<List<Shoe>>> = _newDeals
    private val _productRating = MutableStateFlow<Resource<List<Review>>>(Resource.Loading())
    val productRating : StateFlow<Resource<List<Review>>> = _productRating


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

        getAdidasShoeCategory()
        getPumaShoeCategory()
        getNewShoes()
        getNikeShoesCategory()
        getAllShoes()
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

    private fun getNewShoes() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _newDeals.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .limit(10)
                    .get()
                    .addOnSuccessListener { result ->
                        val shoeList = result.toObjects(Shoe::class.java)

                        val randomShoes = if (shoeList.size >= 5) {
                            shoeList.shuffled().subList(0, 5)
                        } else {
                            shoeList
                        }
//                        for (document in result) {
//                            val shoe = document.toObject(Shoe::class.java)
//                            val shuffledShoe = shoeList.shuffled()
//                            val randomShoe = shuffledShoe.subList(0, minOf(5, shuffledShoe.size))
//                            shoeList.add(shoe)
//                        }
                        _newDeals.value = Resource.Success(randomShoes)
                        Log.d("GET NEW DEALS", "Shoes: $randomShoes")
                    }
                    .addOnFailureListener { e ->
                        _newDeals.value = Resource.Error(e.localizedMessage)
                    }
            } catch (e: Exception) {
                _newDeals.value = Resource.Loading()
                Log.d("GET NEW DEALS", "${e.message}")
            }

        }
    }

    private fun getNikeShoesCategory() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _nike.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .whereEqualTo("brand", "Nike")
                    .limit(5)
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

    private fun getAdidasShoeCategory() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _adidas.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .whereEqualTo("brand", "Adidas")
                    .limit(5)
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

    private fun getPumaShoeCategory() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _puma.value = Resource.Loading()
                fireStore.collection("Shoes")
                    .whereEqualTo("brand", "Puma")
                    .limit(5)
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

    fun getProductRating(
        shoeId: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _productRating.value = Resource.Loading()

                fireStore.collection("reviews")
                    .whereEqualTo("productId", shoeId)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val productReview = mutableListOf<Review>()

                        for (document in querySnapshot){
                            val review = document.toObject(Review::class.java)
                            productReview.add(review)
                        }
                        _productRating.value = Resource.Success(productReview)

                    }
                    .addOnFailureListener{ exception ->
                        _productRating.value = Resource.Error(exception.message)
                        Log.e("PRODUCT REVIEW", exception.message.toString())
                    }
            }catch (e: Exception) {
                _productRating.value = Resource.Error(e.message)
                Log.d("PRODUCT REVIEW", "${e.message}")
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