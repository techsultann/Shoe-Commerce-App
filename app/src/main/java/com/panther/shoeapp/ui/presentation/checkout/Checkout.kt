package com.panther.shoeapp.ui.presentation.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.pay.button.ButtonTheme
import com.google.pay.button.ButtonType
import com.google.pay.button.PayButton
import com.panther.shoeapp.R
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.CardButton
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.utils.PaymentsUtil


@Composable
fun CheckOutScreen(
    navHostController: NavHostController,
    subTotalPrice: String?,
    viewModel: CheckoutViewModel
) {

    val state by viewModel.state.collectAsState()
    val shippingCost by remember {
        mutableDoubleStateOf(3000.00)
    }
    val subTotal by remember {
        mutableDoubleStateOf(subTotalPrice!!.toDouble())
    }

    val totalAmount = shippingCost + subTotal
    val total by remember {
        mutableDoubleStateOf(totalAmount)
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
                modifier = Modifier.padding(16.dp)
            )

             Row(
                 modifier = Modifier
                     .height(65.dp)
                     .padding(16.dp)
                     .fillMaxWidth(),
                 horizontalArrangement = Arrangement.SpaceBetween,
                 verticalAlignment = Alignment.CenterVertically
             ) {
                 PayButton(
                     modifier = Modifier
                         .testTag("payButton")
                         .height(65.dp)
                         .width(81.dp),
                     onClick = {
                         viewModel.requestPayment(priceCents = totalAmount)
                         navHostController.navigate(route = HomeScreenNav.SuccessfulScreen.route)
                               },
                     allowedPaymentMethods = PaymentsUtil.cardPaymentMethod.toString(),
                     type = ButtonType.Pay,
                     enabled = state.googlePayButtonClickable,
                     theme = ButtonTheme.Dark
                 )
                 CardButton(boolean = true, icon = R.drawable.visa_logo, onClick = {})
                 CardButton(boolean = true, icon = R.drawable.master_card, onClick = {})
                 CardButton(boolean = true, icon = R.drawable.ic_round_add, onClick = {})
             }
            
            Text(
                text = "Delivery address",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
            
            Row(
                modifier = Modifier
                    .padding(16.dp)
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
                    Text(
                        text = "Toodely Benson Allentown, New Mexico 31134.",
                        textAlign = TextAlign.Start
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_icon),
                        contentDescription = "edit icon",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }


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

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .requiredHeight(66.dp),
                onClick = {
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

//@Preview
//@Composable
//fun PreviewCheckOutScreen() {
//    CheckOutScreen(rememberNavController(), "", )
//}
