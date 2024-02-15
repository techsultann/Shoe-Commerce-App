package com.panther.shoeapp.ui.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.FieldColor
import com.panther.shoeapp.ui.theme.Red
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.skyBlue

@Composable
fun CartScreen(){

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
                    append("Your Cart\n")
                    pushStyle(style = SpanStyle(fontWeight = FontWeight.Bold))
                    append("List (2)")
                },
                color = navyBlue,
                fontSize = 36.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 46.sp,
                modifier = Modifier.padding(16.dp)
            )

            CartCard(name = "Air Zoom", price = "$650", image = R.drawable.air_zoom)

            CartCard(name = "Air Max Zoom", price = "$720", image = R.drawable.air_max_300)


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
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    fontSize = 16.sp
                )

                Text(
                    text = "$ 1375",
                    fontSize = 16.sp
                )
            }

            ShoeAppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .requiredHeight(66.dp),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Checkout",
                    fontSize = 18.sp
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewCartScreen(){
    CartScreen()
}




@Composable
fun CartCard(
    name: String,
    price: String,
    image: Int
) {

    Surface(
        modifier = Modifier
            .padding(16.dp)
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
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "sneaker image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(200.dp)
                )

            }



            Column() {
                Text(
                    text = name,
                    color = Color(0xFF152354),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                Text(
                    text = price,
                    color = Color(0xFF152354),
                    fontSize = 20.sp
                )

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add icon",
                            modifier = Modifier
                                .background(skyBlue)
                                .clip(CircleShape)
                                .clickable { }
                        )
                    }
                        Text(
                            text = "1",
                            color = Color(0xFF152354),
                            fontSize = 20.sp
                        )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add icon",
                            modifier = Modifier
                                .background(skyBlue)
                                .clip(CircleShape)
                                .clickable { }
                        )
                    }

                }


            }


            Box(modifier = Modifier
                .width(51.dp)
                .height(81.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Red),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "cart icon",
                    tint = Color.White
                )

            }
        }
    }
}

@Preview
@Composable
fun PreviewCartCard() {
    CartCard(name = "Nike Zoom Air", price = "$250", image = R.drawable.air_max_270)
}