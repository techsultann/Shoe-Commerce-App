package com.panther.shoeapp.presentation.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.CartItem
import com.panther.shoeapp.utils.FirebaseManager
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
    private val auth: FirebaseAuth,
    private val firebaseManager: FirebaseManager
) : ViewModel() {

  //  private val _cartItems = MutableStateFlow<Resource<List<CartItem>>>(Resource.Loading())
    val cartItems : StateFlow<Resource<List<CartItem>>> = firebaseManager.cartItems
    private val _totalAmount = MutableStateFlow(0.0)
    val totalAmount: StateFlow<Double> = _totalAmount.asStateFlow()

    init {
        cartItems()
        updateCartAmount()
    }
    fun cartItems() {

        viewModelScope.launch {
            firebaseManager.getCartItems()
            updateCartAmount()
        }
    }



    fun updateCartAmount() {
        val items = cartItems.value.data ?: emptyList()
        _totalAmount.value = items.sumOf { it.price!! * it.quantity!! }
       // Log.d("UPDATE CART AMOUNT CV 45", "Amount: $totalAmount")
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