package com.panther.shoeapp.presentation.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.Review
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : ViewModel() {

    private val _reviewState = MutableStateFlow<Resource<Review>>(Resource.Idle())
    val reviewState: StateFlow<Resource<Review>> = _reviewState

    fun addReview(
        cartItemId: String,
        userReview: String,
        rating: Float
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val userId = auth.currentUser!!.uid
                val displayName = auth.currentUser!!.displayName
                val reviewId = generateUniqueId()

                val review = Review(
                    id = reviewId,
                    productId = cartItemId,
                    userId = userId,
                    rating = rating,
                    userReview = userReview,
                    userDisplayName = displayName
                )

                fireStore.collection("reviews")
                    .document(cartItemId)
                    .set(review)
                    .addOnSuccessListener { _reviewState.value = Resource.Success(review) }
                    .addOnFailureListener { e ->
                        _reviewState.value = Resource.Error(e.message)
                    }

            } catch (e: Exception) {
                _reviewState.value = Resource.Error(e.message)
            }
        }
    }

    private fun generateUniqueId(): String {

        val randomLong = Random().nextLong()
        return "RV_$randomLong"
    }


}

