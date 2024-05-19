package com.panther.shoeapp.presentation.card

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.CreditCard
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditCardVieModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel(){

    private val _uploadCardDetails = MutableStateFlow<Resource<CreditCard>>(Resource.Loading())
    val uploadCardDetails: StateFlow<Resource<CreditCard>> = _uploadCardDetails

    fun saveCardDetails(
        cardType: String,
        cardName: String,
        cardNumber: String
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            _uploadCardDetails.value = Resource.Loading()

            try {
                val cardDetails = CreditCard(
                    cardType = cardType,
                    cardName = cardName,
                    cardNumber = cardNumber,
                    expiryDate = null,
                    cvv = null
                )

                val userId = auth.currentUser!!.uid
                fireStore.collection("users")
                    .document(userId)
                    .collection("cards")
                    .document()
                    .set(cardDetails)
                    .addOnSuccessListener {
                        _uploadCardDetails.value = Resource.Success(cardDetails)
                        Log.d("CARD DETAILS", "Card details saved")
                    }
                    .addOnFailureListener{ e ->
                        _uploadCardDetails.value = Resource.Error(e.localizedMessage)
                        Log.d("CARD DETAILS", "Error Saving Shoe Details ${e.localizedMessage}")
                    }

            } catch (e: Exception) {

                Log.e("CARD DETAILS", "Unable to save card details ${e.stackTrace}")

            }
        }
    }


}