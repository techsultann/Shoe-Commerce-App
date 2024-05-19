package com.panther.shoeapp.presentation.order

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.CartItem
import com.panther.shoeapp.models.Order
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _getOrderItems = MutableStateFlow<Resource<List<Order>>>(Resource.Loading())
    val getOrderItems : StateFlow<Resource<List<Order>>> = _getOrderItems
    private val _itemById = MutableStateFlow<Resource<CartItem>>(Resource.Loading())
    val itemById: StateFlow<Resource<CartItem>> = _itemById

    init {
        getOrderItems()
    }


    fun getShoeById(itemId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = auth.currentUser!!.uid

                fireStore.collection("orders")
                    .document(userId)
                    .collection("orderItems")
                    .document(itemId)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document!= null && document.exists()) {
                            val item = document.toObject(CartItem::class.java)
                            if (item!= null) {
                                _itemById.value = Resource.Success(item)
                            } else {
                                _itemById.value = Resource.Error("Failed to convert document to CartItem.")
                            }
                        } else {
                            _itemById.value = Resource.Error("Document does not exist or is empty.")
                        }
                    }
                    .addOnFailureListener { e ->
                        _itemById.value = Resource.Error(e.localizedMessage)
                    }
            } catch (e: Exception) {
                _itemById.value = Resource.Error(e.message)
            }
        }

    }


    private fun getOrderItems() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                _getOrderItems.value = Resource.Loading()

                val userId = auth.currentUser!!.uid
                fireStore.collection("orders")
                    .document(userId)
                    .collection("orderItems")
                    .get()
                    .addOnSuccessListener { orders ->

                        val orderList = mutableListOf<Order>()
                        for (order in orders){
                            val orderItem = order.toObject(Order::class.java)
                            orderList.add(orderItem)
                        }
                        _getOrderItems.value = Resource.Success(orderList)
                        Log.d("ORDER DETAILS", "ORDER: $orderList")
                    }
                    .addOnFailureListener{ e ->
                        _getOrderItems.value = Resource.Error(e.localizedMessage)

                        Log.d("CARD DETAILS", "Card: ${e.localizedMessage}")
                    }
            } catch (e: Exception) {
                _getOrderItems.value = Resource.Error(e.stackTrace.toString())
            }
        }
    }
}