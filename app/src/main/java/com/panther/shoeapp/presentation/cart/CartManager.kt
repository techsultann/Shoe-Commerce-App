package com.panther.shoeapp.presentation.cart

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartManager() {
    private val _cartTotal = MutableStateFlow(0)
    val cartTotal : StateFlow<Int> = _cartTotal.asStateFlow()

    suspend fun updateCartTotal(cartSize: Int) {
        _cartTotal.emit(cartSize)

    }

    suspend fun clearCart() {
        _cartTotal.emit(0)
        Log.d("CART UPDATE", "Cart Total $cartTotal")
    }

    suspend fun getCartSize() = cartTotal
}