package com.panther.shoeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panther.shoeapp.models.CartItem
import com.panther.shoeapp.utils.FirebaseManager
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseManager: FirebaseManager
) : ViewModel() {

    private val _cartItems = MutableStateFlow<Resource<List<CartItem>>>(Resource.Loading())
    val cartItems : StateFlow<Resource<List<CartItem>>> = firebaseManager.cartItems

    init {
        cartItems()
    }
    fun cartItems() {

        viewModelScope.launch {
            firebaseManager.getCartItems()
        }
    }

}