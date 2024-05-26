package com.panther.shoeapp.presentation.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.CartItem
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _cartItems = MutableStateFlow<Resource<List<CartItem>>>(Resource.Loading())
    val cartItems : StateFlow<Resource<List<CartItem>>> = _cartItems.asStateFlow()
    private val _totalAmount = MutableStateFlow(0.0)
    val totalAmount: StateFlow<Double> = _totalAmount.asStateFlow()

    fun getCartItems() {
        viewModelScope.launch {

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

    fun updateCartAmount() {
        val items = cartItems.value.data ?: emptyList()
        _totalAmount.value = items.sumOf { it.price!! * it.quantity!! }
        Log.d("UPDATE CART AMOUNT", "Amount: $totalAmount")
    }


    fun updateCart(
        shoeId: String,
        quantity: Int?
    ) {

        viewModelScope.launch {

            val userId = auth.currentUser!!.uid
            try {
                fireStore.collection("baskets")
                    .document(userId)
                    .collection("cartItems")
                    .document(shoeId)
                    .update("quantity", quantity)

                    .addOnSuccessListener {
                        Log.d("UPDATE CART", "Product quantity updated successfully")
                        getCartItems()
                    }
                    .addOnFailureListener { e ->
                        Log.w("UPDATE CART", "Error updating product quantity", e)
                    }
            } catch (e: Exception) {
                Log.d("UPDATE CART", "${e.message}")
            }
        }
    }




    fun deleteCart(
        shoeId: String
    ) {

        viewModelScope.launch {

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