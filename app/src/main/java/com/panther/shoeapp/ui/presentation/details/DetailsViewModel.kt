package com.panther.shoeapp.ui.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.CartItem
import com.panther.shoeapp.models.Shoe
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _shoeById = MutableStateFlow<Resource<Shoe>>(Resource.Loading())
    val shoeById: StateFlow<Resource<Shoe>> = _shoeById


    fun getShoeById(shoeId: String) {

        fireStore.collection("Shoes")
            .document(shoeId)
            .get()
            .addOnSuccessListener { document ->
                val shoe = document.toObject(Shoe::class.java)
                _shoeById.value = Resource.Success(shoe!!)
            }
            .addOnFailureListener { e ->
                _shoeById.value = Resource.Error(e.localizedMessage)
            }

    }

    fun addToCart(
        shoeId: String,
        shoeImage: String,
        name: String,
        price: Double
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            val userId = auth.currentUser!!.uid

            val cartItemData = CartItem(
                id = shoeId,
                name = name,
                price = price,
                image = shoeImage,
                quantity = 1
            )

            try {
                fireStore.collection("baskets")
                    .document(userId)
                    .collection("cartItems")
                    .document(shoeId)
                    .set(cartItemData)
                    .addOnSuccessListener {
                        Log.d("ADD TO CART", "Product added to cart successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.w("ADD TO CART", "Error adding product to cart", e)
                    }

            } catch (e: Exception) {
                Log.d("ADD TO CART", "${e.message}")
            }
        }

    }
}