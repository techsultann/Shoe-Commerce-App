package com.panther.shoeapp.presentation.cart

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Notifications
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.AlertDialog
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.FieldColor
import com.panther.shoeapp.ui.theme.Red
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.skyBlue
import com.panther.shoeapp.ui.theme.white
import com.panther.shoeapp.utils.Constants.toCurrency
import com.panther.shoeapp.utils.Resource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    navHostController: NavHostController
){
    val viewModel: CartViewModel = viewModel()
    val itemCount by viewModel.itemCount.collectAsState()
    val cartItemState by viewModel.cartItems.collectAsState()
    val totalAmount by viewModel.totalAmount.collectAsState(initial = 0.0)

    LaunchedEffect(Unit) {

        coroutineScope {
            launch {
                viewModel.getItemCount()
                viewModel.getCartItems()
                viewModel.cartItems.collect{
                    if (it is Resource.Success){
                        viewModel.updateCartAmount()
                    }
                }
            }
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CART",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF152354)

                    )
                },
                navigationIcon = {

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
                .verticalScroll(rememberScrollState())
        ) {

            when (cartItemState) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {

                    val cartItems = cartItemState.data ?: emptyList()

                    Text(
                        text = buildAnnotatedString {
                            append("Your Cart\n")
                            pushStyle(style = SpanStyle(fontWeight = FontWeight.Bold))
                            append("List ($itemCount)")
                        },
                        color = navyBlue,
                        fontSize = 36.sp,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 46.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            contentPadding = PaddingValues(4.dp)
                        ) {

                            items(
                                items = cartItems,
                                key = { cartItem ->
                                    cartItem.id!!
                                }
                            ) { product ->

                                Log.d("CART ITEM", "Cart: $cartItems")

                                CartCard(
                                    name = product.name.toString(),
                                    price = product.price!!.toCurrency(),
                                    image = product.image.toString(),
                                    id = product.id,
                                    initialQuantity = product.quantity
                                )
                            }

                        }



                }
                is Resource.Error -> {
                    val errorMessage = (cartItemState as Resource.Error).message
                    // Show error UI (e.g., display error message)
                    Log.d("CART ITEM", "Cart: $errorMessage")
                }
                is Resource.Idle -> {

                }

            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Do you have any voucher?",
                color = Color(0xFF152354),
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

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

                Log.d("TOTAL AMOUNT", "Total: $totalAmount")

                Text(
                    text = totalAmount.toCurrency(),
                    fontSize = 16.sp
                )
            }

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 100.dp)
                    .requiredHeight(66.dp),
                onClick = { navHostController.navigate( route = "${HomeScreenNav.CheckoutScreen.route}/$totalAmount" )  }
            ) {
                Text(
                    text = "Checkout",
                    fontSize = 18.sp
                )
            }


        }
    }
}


@Composable
fun CartCard(
    name: String,
    price: String,
    image: String,
    id: String?,
    initialQuantity: Int? = 1,
    viewModel: CartViewModel = viewModel()
) {
    var quantity by rememberSaveable {
        mutableIntStateOf(initialQuantity!!)
    }
    val openAlertDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(6.dp),
        color = Color.Transparent
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(24.dp))
                    .background(FieldColor),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                )

            }


            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    color = Color(0xFF152354),
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(100.dp),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    text = price,
                    color = Color(0xFF152354),
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    modifier = Modifier
                        .width(100.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(shape = CircleShape),
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp,
                        color = skyBlue
                    ){
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add icon",
                            modifier = Modifier
                                .size(20.dp)
                                .background(skyBlue)
                                .clip(CircleShape)
                                .clickable {
                                    if (quantity >= 1) {
                                        quantity++
                                        viewModel.updateCart(id.toString(), quantity)
                                    }
                                },
                            tint = white
                        )
                    }

                        Text(
                            text = quantity.toString(),
                            color = Color(0xFF152354),
                            fontSize = 20.sp
                        )

                    Surface(
                        modifier = Modifier
                            .border(BorderStroke(1.dp, skyBlue), CircleShape)
                            .size(30.dp)
                            .clip(shape = CircleShape),
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp,
                        color = white
                    ){
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Add icon",
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    quantity -= 1
                                    viewModel.updateCart(id.toString(), quantity)
                                },
                            tint = skyBlue

                        )
                    }

                }


            }

            Box(modifier = Modifier
                .width(51.dp)
                .height(81.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Red)
                .clickable {
                    openAlertDialog.value = true

                },
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "delete icon",
                    tint = Color.White
                )

            }

        }
        when {
            openAlertDialog.value -> {

                AlertDialog(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        if (id != null) {
                            viewModel.deleteCart(id)
                        }
                        openAlertDialog.value = false
                                     },
                    dialogTitle = "Are your sure you wanna remove this item?"
                )
            }
        }

    }
}

