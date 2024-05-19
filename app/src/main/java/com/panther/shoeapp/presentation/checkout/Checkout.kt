package com.panther.shoeapp.presentation.checkout

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.models.api_response.Customer
import com.panther.shoeapp.models.api_response.PaymentRequest
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.utils.Constants.toCurrency
import com.panther.shoeapp.utils.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun CheckOutScreen(
    navHostController: NavHostController,
    subTotalPrice: String?,
    viewModel: CheckoutViewModel = viewModel()
) {

    val cardState by viewModel.getSavedCards.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val name by viewModel.displayName.collectAsState()
    val email by viewModel.userEmail.collectAsState()
    val shippingCost by remember { mutableDoubleStateOf(3000.00) }
    val subTotal by remember { mutableDoubleStateOf(subTotalPrice!!.toDouble()) }
    val totalAmount by rememberSaveable {
        mutableDoubleStateOf( shippingCost + subTotal )
    }
    val addressList = viewModel.getAddress.collectAsState()
    val addressData = addressList.value.data
    val userData by viewModel.userData.collectAsState()
    val userDetails = userData.data
   // val total by remember { mutableDoubleStateOf(totalAmount) }
    val firstAddressData = addressData?.first()

    val uriHandler = LocalUriHandler.current

    //observe the payment state from the view model
    val paymentState = viewModel.paymentState.collectAsState()

    // When the payment link is available, call this function to redirect the user
    fun redirectToPaymentLink(paymentLink: String) {
        uriHandler.openUri(paymentLink)
    }

    when (val response = paymentState.value) {
        is Resource.Error -> {

        }
        is Resource.Loading -> {

        }
        is Resource.Success -> {
            val paymentResponse = response.data?.data
            if (paymentResponse != null) {
                redirectToPaymentLink(paymentResponse.link)
            }
        }
        is Resource.Idle -> {

        }
    }

    LaunchedEffect(key1 = Unit, key2 = paymentState) {
        coroutineScope {
            launch {
                viewModel.getSavedCards()
                viewModel.getCartItems()
                viewModel.getAddressList()
            }

        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CHECKOUT",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF152354)

                    )
                },
                navigationIcon = {
                    Surface(
                        modifier = Modifier
                            .size(height = 56.dp, width = 56.dp)
                            .clip(shape = CircleShape),
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp
                    ) {
                        IconButton(
                            onClick = {
                                navHostController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Navigation back icon",
                                tint = Color.Unspecified
                            )
                        }

                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "light theme icon",
                            tint = Color.Unspecified
                        )

                    }

                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            Text(
                text = buildAnnotatedString {
                    append("Choose a payment\n")
                    pushStyle(style = SpanStyle(fontWeight = FontWeight.Bold))
                    append("Method")
                },
                color = navyBlue,
                fontSize = 36.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 46.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Delivery address",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = "Change",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { navHostController.navigate(HomeScreenNav.AddressListScreen.route) }
                )
            }

            if (addressData?.isEmpty() == true) {
                Text(
                    text = "Add Address",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { navHostController.navigate(HomeScreenNav.AddAddressScreen.route) }
                )
            } else {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(150.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "person icon"
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "${firstAddressData?.lastName} ${firstAddressData?.firstName}",
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = "person icon"
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            if (firstAddressData != null) {
                                Text(
                                    text = "${firstAddressData.state}, ${firstAddressData.city}, ${firstAddressData.street}, ${firstAddressData.postcode}",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Call,
                                contentDescription = "person icon"
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            if (firstAddressData != null) {
                                Text(
                                    text = firstAddressData.phoneNumber!!,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        }

                    }
                }


            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(
                thickness = 1.dp,
                color = Color.LightGray
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtotal",
                    fontSize = 16.sp
                )

                Text(
                    text = subTotal.toCurrency(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Shipping Cost",
                    fontSize = 16.sp
                )

                Text(
                    text = shippingCost.toCurrency(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    fontSize = 16.sp
                )

                Text(
                    text = totalAmount.toCurrency(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val orderList = cartItems.data
            Log.d("ORDER", "Order: $orderList")

           // val orderListToJson = Json.encodeToString(orderList)
            //Log.d("JSON", "JSON DATA: $orderListToJson" )

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .requiredHeight(66.dp),
                onClick = {
                    if (firstAddressData != null) {
                        viewModel.createOrder(
                            totalPrice = totalAmount,
                            cartItem = orderList!!,
                            address = "${firstAddressData.state}, ${firstAddressData.city}, ${firstAddressData.street}, ${firstAddressData.postcode}",
                            name = "${firstAddressData.lastName} ${firstAddressData.firstName}",
                            phoneNumber = firstAddressData.phoneNumber!!
                        )
                    }
                        viewModel.makeFlutterWavePayment(
                            PaymentRequest(
                                amount = "2000",
                                currency = "NGN",
                                customer = Customer(
                                    name = name,
                                    email = email,
                                    phonenumber = "09130221615"
                                ),
                                redirectUrl = "https://techsultan.com",
                                txRef = "txf-1234"
                            )
                        )
                   // navHostController.navigate(route = HomeScreenNav.SuccessfulScreen.route)
                }
            ) {
                Text(
                    text = "Pay Now",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCheckOutScreen() {
    CheckOutScreen(rememberNavController(), "" )
}
