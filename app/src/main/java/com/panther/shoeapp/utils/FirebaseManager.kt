package com.panther.shoeapp.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.panther.shoeapp.models.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class FirebaseManager @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {
    private val _cartItems = MutableStateFlow<Resource<List<CartItem>>>(Resource.Loading())
    val cartItems : StateFlow<Resource<List<CartItem>>> = _cartItems.asStateFlow()
    private var totalCartItem = 0

    fun getCartItems() {

        try {
            val userId = auth.currentUser!!.uid
            fireStore.collection("baskets")
                .document(userId)
                .collection("cartItems")
                .addSnapshotListener { value, error ->

                    if (value != null) {
                        val shoeList = mutableListOf<CartItem>()
                        val snapshot =  value.documents.map { it.toObject<CartItem>() }
                        snapshot.toMutableList().filterNotNull().let { shoeList.addAll(it) }
//
                        _cartItems.value = Resource.Success(shoeList)
                        totalCartItem = shoeList.size

                    }

                    if (error != null) {
                        Log.d("CART LISTENER 59", "Listener: ${error.message}")
                    }

                }

        } catch (e: Exception) {
            _cartItems.value = Resource.Error(e.message)
            Log.e("GET CART ITEMS", "Exception: ${e.message}")
        }

    }


}