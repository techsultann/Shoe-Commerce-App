package com.panther.shoeapp.ui.presentation.address

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.Address
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _createAddress = MutableStateFlow<Resource<Address>>(Resource.Loading())
    val createAddress: StateFlow<Resource<Address>> = _createAddress
    private val _getAddress = MutableStateFlow<Resource<List<Address>>>(Resource.Loading())
    val getAddress: StateFlow<Resource<List<Address>>> = _getAddress
    private val _updateAddress = MutableStateFlow<Resource<Address>>(Resource.Loading())
    val updateAddress: StateFlow<Resource<Address>> = _updateAddress
    private val _addressById = MutableStateFlow<Resource<Address>>(Resource.Loading())
    val addressById: StateFlow<Resource<Address>> = _addressById


    fun getAddressList() {

        viewModelScope.launch(Dispatchers.IO) {

            val userId = auth.currentUser!!.uid
            try {
                _getAddress.value = Resource.Loading()

                fireStore.collection("users")
                    .document(userId)
                    .collection("User Address")
                    .get()
                    .addOnSuccessListener { result ->

                        val addressList = result.toObjects(Address::class.java)
                        _getAddress.value = Resource.Success(addressList)

                        Log.d("GET ALL SHOES", "Shoes: $addressList")
                    }
                    .addOnFailureListener { e ->
                        _getAddress.value = Resource.Error(e.localizedMessage)
                    }
            } catch (e: Exception) {
                _getAddress.value = Resource.Loading()
                Log.d("GET ALL SHOES", "${e.message}")
            }

        }
    }
    fun createAddress(
        firstName: String,
        lastName: String,
        street: String,
        apartment: String,
        city: String,
        postcode: String,
        phoneNumber: String,
        state: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                val userId = auth.currentUser!!.uid
                val addressId = generateAddressId()

                val address = Address(
                    id = addressId,
                    firstName = firstName,
                    lastName = lastName,
                    street = street,
                    apartment = apartment,
                    city = city,
                    postcode = postcode,
                    phoneNumber = phoneNumber,
                    state = state
                )

                fireStore.collection("users")
                    .document(userId)
                    .collection("User Address")
                    .document(addressId)
                    .set(address)
                    .addOnSuccessListener {

                        _createAddress.value = Resource.Success(address)
                        Log.d("ADDRESS", "Address saved")
                    }
                    .addOnFailureListener { e ->
                        _createAddress.value = Resource.Error(e.localizedMessage)
                        Log.d("ADDRESS", "Error Saving User Address ${e.localizedMessage}")
                    }
            } catch (e: Exception) {

                Log.e("ADDRESS", "Unable to save user address ${e.message}")

            }
        }
    }

    fun updateAddress(
        addressId: String,
        firstName: String,
        lastName: String,
        street: String,
        apartment: String,
        city: String,
        postcode: String,
        state: String,
        phoneNumber: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                val userId = auth.currentUser!!.uid

                val addressUpdate = Address(
                    addressId,
                    firstName,
                    lastName,
                    street,
                    apartment,
                    city,
                    postcode,
                    state,
                    phoneNumber
                )

                fireStore.collection("users")
                    .document(userId)
                    .collection("User Address")
                    .document(addressId)
                    .set(addressUpdate)
                    .addOnSuccessListener {

                        _updateAddress.value = Resource.Success(addressUpdate)
                    }
                    .addOnFailureListener { e ->
                        _updateAddress.value = Resource.Error(e.localizedMessage)
                        Log.d("ADDRESS", "Error Saving User Address ${e.localizedMessage}")
                    }
            } catch (e: Exception) {

                Log.e("ADDRESS", "Unable to save user address ${e.message}")

            }
        }
    }

    fun getAddressById(addressId: String?) {

        val userId = auth.currentUser!!.uid

        if (addressId != null) {
            fireStore.collection("users")
                .document(userId)
                .collection("User Address")
                .document(addressId)
                .get()
                .addOnSuccessListener { document ->

                    val userAddressDetails = document.toObject(Address::class.java)
                    Log.d("GET USER ADDRESS", "Addresss: $userAddressDetails")

                    _addressById.value = Resource.Success(userAddressDetails!!)
                }
                .addOnFailureListener { e ->
                    _addressById.value = Resource.Error(e.localizedMessage)
                }
        }

    }

    fun deleteAddress(
        addressId: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val userId = auth.currentUser!!.uid

                fireStore.collection("users")
                    .document(userId)
                    .collection("User Address")
                    .document(addressId)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("DELETE ADDRESS", "Product removed from cart successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.w("DELETE ADDRESS", "Error removing product from cart", e)
                    }


            } catch (e: Exception) {
                Log.d("DELETE ADDRESS", "${e.message}")
            }

        }
    }


}



private fun generateAddressId(): String {
    // You can use any unique identifier generation mechanism here, such as UUID
    return UUID.randomUUID().toString()
}