package com.panther.shoeapp.ui.presentation.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.CartItem
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _cartItems = MutableStateFlow<Resource<List<CartItem>>>(Resource.Loading())
    val cartItems : StateFlow<Resource<List<CartItem>>> = _cartItems
    private val _itemCount = MutableStateFlow(0)
    val itemCount = _itemCount


    init {
        getCartItems()
    }

    private fun getCartItems() {
        viewModelScope.launch(Dispatchers.IO) {

            val userId = auth.currentUser!!.uid

            try {

                _cartItems.value = Resource.Loading()

                fireStore.collection("baskets")
                    .document(userId)
                    .collection("cartItems")
                    .get()
                    .addOnSuccessListener { result ->

                        val shoeList = mutableListOf<CartItem>()
                        for (document in result) {
                            val cart = document.toObject(CartItem::class.java)
                            shoeList.add(cart)
                        }
                        _cartItems.value = Resource.Success(shoeList)
                        Log.d("GET CART ITEMS", "Cart Items: $shoeList")
                    }
                    .addOnFailureListener { e ->
                        _cartItems.value = Resource.Error(e.localizedMessage)
                        Log.e("GET CART ITEMS", "Error fetching cart items: ${e.message}")
                    }

            } catch (e: Exception) {
                Log.d("CART ITEMS", " Cart Items: ${e.message}")
                Log.e("GET CART ITEMS", "Exception: ${e.message}")
            }
        }
    }

    fun updateCart(
        shoeId: String,
        quantity: Int?
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            val userId = auth.currentUser!!.uid
            try {
                fireStore.collection("baskets")
                    .document(userId)
                    .collection("cartItems")
                    .document(shoeId)
                    .update("quantity", quantity)
                    .addOnSuccessListener {
                        Log.d("UPDATE CART", "Product quantity updated successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.w("UPDATE CART", "Error updating product quantity", e)
                    }
            } catch (e: Exception) {
                Log.d("UPDATE CART", "${e.message}")
            }
        }
    }

    fun getItemCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = auth.currentUser!!.uid

            val countQuery = fireStore.collection("baskets")
                .document(userId)
                .collection("cartItems")
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



    fun deleteCart(
        shoeId: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val userId = auth.currentUser!!.uid

                fireStore.collection("baskets")
                    .document(userId)
                    .collection("cartItems")
                    .document(shoeId)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("DELETE CART", "Product removed from cart successfully")
                        getCartItems()
                    }
                    .addOnFailureListener { e ->
                        Log.w("DELETE CART", "Error removing product from cart", e)
                    }


            } catch (e: Exception) {
                Log.d("DELETE CART", "${e.message}")
            }

        }
    }
}