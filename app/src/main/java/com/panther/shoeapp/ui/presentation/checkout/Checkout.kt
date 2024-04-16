package com.panther.shoeapp.ui.presentation.checkout

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.R
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.PaymentCard
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.navyBlue
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun CheckOutScreen(
    navHostController: NavHostController,
    subTotalPrice: String?
) {

    val viewModel: CheckoutViewModel = viewModel()
    val cardState by viewModel.getSavedCards.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val shippingCost by remember { mutableDoubleStateOf(3000.00) }
    val subTotal by remember { mutableDoubleStateOf(subTotalPrice!!.toDouble()) }
    val totalAmount by rememberSaveable {
        mutableDoubleStateOf( shippingCost + subTotal )
    }
   // val total by remember { mutableDoubleStateOf(totalAmount) }
    val isEditMode = remember { mutableStateOf(false) }
    var addressText by rememberSaveable { mutableStateOf("Add your address here") }

    LaunchedEffect(key1 = Unit) {
        coroutineScope {
            launch {
                viewModel.getSavedCards()
                viewModel.getCartItems()
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

                            }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
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

            val cardList = cardState.data ?: emptyList()

            if (cardList.isEmpty()) {

                Text(
                    text = "Add a Payment Method",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    item {

                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            shape = CircleShape,
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 8.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Add a payment Method")

                                Spacer(modifier = Modifier.width(8.dp))

                                Icon(painter = painterResource(id = R.drawable.ic_round_add), contentDescription = "add icon button")
                            }

                        }
                    }

                    items(cardList) { card ->

                        val cardImage = when (card.cardType) {
                            "Master Card" -> { R.drawable.master_card }
                            "VISA" -> { R.drawable.visa_logo }
                            "VERVE" -> { R.drawable.master_card }
                            else -> R.drawable.card_icon
                        }

                        PaymentCard(image = cardImage, cardNo = card.cardNumber.toString())
                    }
                }
            }


            Text(
                text = "Delivery address",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
            
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.location_map),
                    contentDescription = "location image",
                    modifier = Modifier.size(100.dp)
                )

                Column(
                    modifier = Modifier
                        .width(200.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Home address",
                        fontSize = 18.sp
                    )
                    if (isEditMode.value) {

                        OutlinedTextField(
                            value = addressText,
                            onValueChange = { addressText = it },
                            colors = TextFieldDefaults.colors(),
                            modifier = Modifier
                                .border(0.dp, Color.Transparent),
                            keyboardOptions = KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            )

                        )
                    } else {

                        Text(
                            text = addressText,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis,
                            fontStyle = FontStyle.Italic
                        )
                    }

                }

                IconButton(
                    onClick = { isEditMode.value = !isEditMode.value }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_icon),
                        contentDescription = "edit icon",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }

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
                    text = "N $subTotalPrice",
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
                    text = "N $shippingCost",
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
                    text = "N $totalAmount",
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
                    viewModel.createOrder(
                        totalPrice = totalAmount,
                        cartItem = orderList!!,
                        address = addressText
                    )
                    viewModel.clearCartItem()
                    navHostController.navigate(route = HomeScreenNav.SuccessfulScreen.route)
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
