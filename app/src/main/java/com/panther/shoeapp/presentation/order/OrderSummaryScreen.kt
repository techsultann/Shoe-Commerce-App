package com.panther.shoeapp.presentation.order

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.panther.shoeapp.R
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.FieldColor
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.skyBlue
import kotlin.math.roundToInt

@Composable
fun OrderSummaryScreen(
    viewModel: OrderViewModel = viewModel(),
    navHostController: NavHostController,
    orderId: String?,
    itemName: String?,
    itemPrice: String?,
    itemImage: String?,
    orderDate: String?,
    cartItemId: String?,
) {
    val itemState by viewModel.itemById.collectAsState()
    val itemDetails = itemState.data

    var status by rememberSaveable { mutableStateOf("Pending") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ORDER SUMMARY",
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
                            onClick = { navHostController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "navigation back icon",
                                tint = Color.Unspecified
                            )
                        }

                    }
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "light theme icon",
                            tint = navyBlue,
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }

                }

            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {

            Text(
                text = buildAnnotatedString {
                    append("Order Tracking \n")
                    pushStyle(style = SpanStyle(fontWeight = FontWeight.Bold))
                    append("Information")
                },
                color = navyBlue,
                fontSize = 36.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(16.dp),
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(8.dp))


            OrderSummaryCard(
                orderId = orderId!!,
                image = itemImage!!,
                name = itemName!!,
                price = itemPrice!!
            )


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Location",
                color = navyBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.start_location),
                    contentDescription = "start location icon",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(30.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Order")

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Order Date",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = orderDate!!,
                        textAlign = TextAlign.End
                    )
                }

            }

            Box(
                Modifier
                    .width(4.dp)
                    .height(80.dp)
                    .background(skyBlue, DottedShape(step = 15.dp))
//                    .background(
//                        color = if (/*isDelivered()*/) {
//                            Color(0xFF16AB1D)
//                        } else
//                            Color.Gray,
//                        shape = DottedShape(step = 15.dp)
//                    )
            )


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.end_location),
                    contentDescription = "end location icon",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(30.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "Delivered")

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Delivery Date",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                    Text(
                        text = "12pm",
                        textAlign = TextAlign.End
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Related Information",
                color = navyBlue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "person icon",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Adekunle",
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "Jeremiah",
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                }

                IconButton(
                    onClick = { /*TODO*/ },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = skyBlue,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Phone,
                        contentDescription = "call icon",
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .size(50.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .requiredHeight(66.dp),
                onClick = {
                    val cartItemImage = Uri.encode(itemImage)
                    navHostController.navigate("${HomeScreenNav.ReviewScreen.route}/$cartItemImage/$cartItemId")
                }
            ) {
                Text(
                    text = "Write a review",
                    fontSize = 18.sp
                )
            }


        }
    }

}

    private data class DottedShape(
        val step: Dp,
    ) : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density,
        ) = Outline.Generic(Path().apply {
            val stepPx = with(density) { step.toPx() }
            val stepsCount = (size.height / stepPx).roundToInt()
            val actualStep = size.height / stepsCount
            val dotSize = Size(width = size.width, height = actualStep / 2)
            for (i in 0 until stepsCount) {
                addRect(
                    Rect(
                        offset = Offset(y = i * actualStep, x = 30f),
                        size = dotSize
                    )
                )
            }
            close()
        })
    }

    @Composable
    fun OrderSummaryCard(
        orderId: String,
        image: String,
        name: String,
        price: String
    ) {


        Surface(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(FieldColor, shape = RoundedCornerShape(24.dp))
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(24.dp))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(FieldColor),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "shoe image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(100.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceAround
                ) {

                    Text(
                        text = "Code",
                        color = Color(0xFF152354),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = orderId,
                        color = Color(0xFF152354),
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = name,
                        color = Color(0xFF152354),
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = price,
                        color = Color(0xFF152354),
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )

                }


            }
        }

    }
