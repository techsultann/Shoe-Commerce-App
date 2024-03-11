package com.panther.shoeapp.ui.presentation.checkout

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.wallet.IsReadyToPayRequest
import com.google.android.gms.wallet.PaymentData
import com.google.android.gms.wallet.PaymentDataRequest
import com.google.android.gms.wallet.PaymentsClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.utils.PaymentsUtil
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : AndroidViewModel(Application()) {

   data class State(
      val googlePayAvailable: Boolean? = false,
      val googlePayButtonClickable: Boolean = true,
      val checkoutSuccess: Boolean = false,
   )

   private val _state = MutableStateFlow(State())
   val state: StateFlow<State> = _state.asStateFlow()

   //private val paymentsClient : PaymentsClient = PaymentsUtil.createPaymentsClient(getApplication<Application>().applicationContext)

   init {
      checkGooglePayAvailability()
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