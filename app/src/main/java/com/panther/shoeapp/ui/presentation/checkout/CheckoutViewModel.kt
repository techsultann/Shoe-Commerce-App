package com.panther.shoeapp.ui.presentation.checkout

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.CreditCard
import com.panther.shoeapp.utils.PaymentsUtil
import com.panther.shoeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class CheckoutViewModel @Inject constructor(
   private val fireStore: FirebaseFirestore,
   private val auth: FirebaseAuth,
   private val paymentsClient: PaymentsClient
) : ViewModel() {

   private val _getSavedCards = MutableStateFlow<Resource<List<CreditCard>>>(Resource.Loading())
   val getSavedCards : StateFlow<Resource<List<CreditCard>>> = _getSavedCards

   data class State(
      val googlePayAvailable: Boolean? = false,
      val googlePayButtonClickable: Boolean = true,
      val checkoutSuccess: Boolean = false,
   )

   private val _state = MutableStateFlow(State())
   val state: StateFlow<State> = _state.asStateFlow()

   init {
      checkGooglePayAvailability()
   }

   fun getSavedCards() {

      viewModelScope.launch(Dispatchers.IO) {

         try {
            _getSavedCards.value = Resource.Loading()

            val userId = auth.currentUser!!.uid
            fireStore.collection("users")
               .document(userId)
               .collection("cards")
               .get()
               .addOnSuccessListener { cards ->

                  val cardList = mutableListOf<CreditCard>()
                  for (card in cards){
                     val creditCard = card.toObject(CreditCard::class.java)
                     cardList.add(creditCard)
                  }
                  _getSavedCards.value = Resource.Success(cardList)
                  Log.d("CARD DETAILS", "Card: $cardList")
               }
               .addOnFailureListener{ e ->
                  _getSavedCards.value = Resource.Error(e.localizedMessage)

                  Log.d("CARD DETAILS", "Card: ${e.localizedMessage}")
               }
         } catch (e: Exception) {
            _getSavedCards.value = Resource.Error(e.stackTrace.toString())
         }
      }
   }

   private fun checkGooglePayAvailability() {

      val isReadyToPayJson = PaymentsUtil.isReadyToPayRequest() ?: return
      val request = IsReadyToPayRequest.fromJson(isReadyToPayJson.toString()) ?: return

      // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
      // OnCompleteListener to be triggered when the result of the call is known.
      val task = paymentsClient.isReadyToPay(request)

      task.addOnCompleteListener { completedTask ->
         try {
            completedTask.getResult(ApiException::class.java)
         } catch (exception: ApiException) {
            // Process error
            Log.w("isReadyToPay failed", exception)
         }
      }
   }


   fun requestPayment(priceCents: Double) {

      val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents)
      val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

      viewModelScope.launch {
         _state.update { currentState -> currentState.copy(googlePayButtonClickable = false) }
         try {
            val paymentData = paymentsClient.loadPaymentData(request).await()
            handlePaymentSuccess(paymentData)
         } catch (exception: ApiException) {
            handleError(exception.statusCode, exception.message)
         } finally {
            _state.update { currentState -> currentState.copy(googlePayButtonClickable = true) }
         }
      }
   }

   fun getLoadPaymentDataTask(priceCents: Double): Task<PaymentData> {

      val paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents)
      val request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString())

      return paymentsClient.loadPaymentData(request)
   }

   fun handlePaymentSuccess(paymentData: PaymentData) {
      // Implement your success logic here (e.g., call your backend for processing)
      _state.update { currentState -> currentState.copy(checkoutSuccess = true) }
   }

   fun handleError(statusCode: Int, message: String?) {
      // Implement your error handling logic here (e.g., display an error message)
   }

   fun setGooglePayButtonClickable(clickable:Boolean) {
      _state.update { currentState ->
         currentState.copy(googlePayButtonClickable = clickable)
      }
   }

   fun checkoutSuccess() {
      _state.update { currentState ->
         currentState.copy(checkoutSuccess = true)
      }
   }

}