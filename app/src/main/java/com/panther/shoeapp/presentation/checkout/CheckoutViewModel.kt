package com.panther.shoeapp.presentation.checkout

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
import com.panther.shoeapp.api.FlutterWaveApi
import com.panther.shoeapp.models.Address
import com.panther.shoeapp.models.CartItem
import com.panther.shoeapp.models.CreditCard
import com.panther.shoeapp.models.Order
import com.panther.shoeapp.models.User
import com.panther.shoeapp.models.api_response.PaymentRequest
import com.panther.shoeapp.models.api_response.PaymentResponse
import com.panther.shoeapp.utils.Constants.SECRET_KEY
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class CheckoutViewModel @Inject constructor(
   private val fireStore: FirebaseFirestore,
   private val auth: FirebaseAuth,
   private val paymentsClient: PaymentsClient,
   private val apiService: FlutterWaveApi
) : ViewModel() {

   private val _getSavedCards = MutableStateFlow<Resource<List<CreditCard>>>(Resource.Loading())
   val getSavedCards : StateFlow<Resource<List<CreditCard>>> = _getSavedCards
   private val _cartItems = MutableStateFlow<Resource<List<CartItem>>>(Resource.Loading())
   val cartItems : StateFlow<Resource<List<CartItem>>> = _cartItems.asStateFlow()
   private val _createOrder = MutableStateFlow<Resource<Order>>(Resource.Loading())
   val createOrder: StateFlow<Resource<Order>> = _createOrder
   private val _getAddress = MutableStateFlow<Resource<List<Address>>>(Resource.Loading())
   val getAddress: StateFlow<Resource<List<Address>>> = _getAddress
   private val _paymentState = MutableStateFlow<Resource<PaymentResponse>>(Resource.Loading())
   val paymentState: StateFlow<Resource<PaymentResponse>> = _paymentState
   private val _displayName = MutableStateFlow("")
   val displayName : StateFlow<String> = _displayName
   private val _userEmail = MutableStateFlow("")
   val userEmail : StateFlow<String> = _userEmail
   private val _userData = MutableStateFlow<Resource<User>>(Resource.Loading())
   val userData: StateFlow<Resource<User>> = _userData
   private val _verifyPaymentState = MutableStateFlow<Resource<PaymentResponse>>(Resource.Loading())
   val verifyPaymentState: StateFlow<Resource<PaymentResponse>> = _verifyPaymentState

   data class State(
      val googlePayAvailable: Boolean? = false,
      val googlePayButtonClickable: Boolean = true,
      val checkoutSuccess: Boolean = false,
   )

   private val _state = MutableStateFlow(State())
   val state: StateFlow<State> = _state.asStateFlow()

   init {
      displayName()
      userEmail()
   }

   fun verifyPayment(id: String) {

      viewModelScope.launch(Dispatchers.IO) {
         try {
             val response = apiService.verifyFlutterWavePayment(SECRET_KEY, id)

            if (response.isSuccessful && response.body() != null) {

               _verifyPaymentState.value = Resource.Success(response.body()!!)
            } else {
               _verifyPaymentState.value = Resource.Error("Error: ${response.message()}")
               Log.d("VERIFY PAYMENT", "Verify Payment Error: ${response.message()}")
            }
         } catch (e:Exception) {
            _paymentState.value = Resource.Error("Exception: ${e.message}")
            Log.d("VERIFY PAYMENT", "Verify Payment Error: ${e.message}")
         }
      }

   }

   fun makeFlutterWavePayment(paymentRequest: PaymentRequest) {

      viewModelScope.launch(Dispatchers.IO) {

         try {

            val response = apiService.flutterWavePayment(SECRET_KEY, paymentRequest)

            if (response.isSuccessful && response.body() != null) {
               _paymentState.value = Resource.Success(response.body()!!)

               Log.d("FLUTTERWAVE", "Wave Payment: ${response.message()}")

            } else {

               _paymentState.value = Resource.Error("Error: ${response.message()}")
               Log.d("FLUTTERWAVE", "Wave Payment Error: ${response.message()}")
            }
         } catch (e: Exception) {

            _paymentState.value = Resource.Error("Exception: ${e.message}")
            Log.d("FLUTTERWAVE", "Wave Payment Error: ${e.message}")
         }

      }

   }

   private fun displayName() {
      viewModelScope.launch(Dispatchers.IO) {
         val currentUser = auth.currentUser
         if (currentUser != null) {
            try {
               _displayName.value = currentUser.displayName.toString()
               Log.d("PROFILE", "Profile Name: ${currentUser.displayName}")
            } catch (e: Exception) {
               // Handle errors gracefully, e.g., log the error or display a message
               Log.w("HomeViewModel", "Error retrieving display name: $e")
            }
         }
      }
   }

   private fun userEmail() {
      viewModelScope.launch(Dispatchers.IO) {
         val currentUser = auth.currentUser
         if (currentUser != null) {
            try {
               _userEmail.value = currentUser.email.toString()
               Log.d("PROFILE", "Profile Email: ${currentUser.email}")

            } catch (e: Exception) {
               // Handle errors gracefully, e.g., log the error or display a message
               Log.w("HomeViewModel", "Error retrieving user email: $e")
            }
         }
      }
   }

   private fun getUserData() {

      val currentUser = auth.currentUser
      val userId = currentUser?.uid

      viewModelScope.launch(Dispatchers.IO) {

         try {
            if (userId != null) {
               fireStore.collection("users")
                  .document(userId)
                  .get()
                  .addOnSuccessListener { document ->
                     val user = document.toObject(User::class.java)
                     _userData.value = Resource.Success(user!!)
                  }
                  .addOnFailureListener { e ->
                     _userData.value = Resource.Error(e.localizedMessage)
                  }
            }
         } catch (e: Exception) {

         }
      }
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

   fun getCartItems() {

      viewModelScope.launch(Dispatchers.IO) {

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

   fun createOrder(
      totalPrice: Double,
      cartItem: List<CartItem>,
      address: String,
      name: String,
      phoneNumber: String
   ) {

      viewModelScope.launch(Dispatchers.IO) {

         try {

            val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDates = simpleDate.format(Date())
            val userId = auth.currentUser!!.uid
            val orderId = generateUniqueShoeId()

            val order = Order(
               orderId = "OD-$orderId",
               userId = userId,
               totalPrice = totalPrice,
               status = "Pending",
               timeStamp = currentDates,
               address = address,
               cartItem = cartItem.associateBy { it.id ?: "" },
               name = name,
               phoneNumber = phoneNumber
            )

            fireStore.collection("orders")
               .document(userId)
               .collection("orderItems")
               .document()
               .set(order)
               .addOnSuccessListener {

                  _createOrder.value = Resource.Success(order)
                  clearCartItem()
                  Log.d("ORDER DETAILS", "Order details saved")
               }
               .addOnFailureListener { e ->
                  _createOrder.value = Resource.Error(e.localizedMessage)
                  Log.d("ORDER DETAILS", "Error Adding to Cart ${e.localizedMessage}")
               }
         } catch (e: Exception) {

            Log.e("ORDER", "Unable to save order details ${e.message}")

         }
      }
   }

   private fun clearCartItem() {

      viewModelScope.launch(Dispatchers.IO) {

         try {
            val userId = auth.currentUser!!.uid
            fireStore.collection("baskets")
               .document(userId)
               .collection("cartItems")
               .document()
               .delete()
               .addOnSuccessListener {
                  Log.d("DELETE CART ITEMS", "Cart cleared successfully")
               }
               .addOnFailureListener {
                  Log.d("DELETE CART ITEMS", "Could not clear cart items")
               }

         } catch (e: Exception) {
            Log.d("DELETE CART", "${e.message}")
         }
      }

   }

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

   fun sendNotificationToAdmin() {

      viewModelScope.launch(Dispatchers.IO) {


      }
   }

   private fun generateUniqueShoeId(): String {
      // You can use any unique identifier generation mechanism here, such as UUID
      return UUID.randomUUID().toString()
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