package com.panther.shoeapp.ui.presentation.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.FieldColor
import com.panther.shoeapp.ui.theme.navyBlue

@Composable
fun CategoryScreen(
    navHostController: NavHostController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CATEGORY",
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
                            painter = painterResource(id = R.drawable.notification),
                            contentDescription = "light theme icon",
                            tint = Color.Unspecified
                        )

                    }

                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(paddingValues = padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            Text(
                text = buildAnnotatedString {
                    append("Shoes Available for \n")
                    pushStyle(style = SpanStyle(fontWeight = FontWeight.Bold))
                    append("Men")
                },
                color = navyBlue,
                fontSize = 36.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(16.dp),
                lineHeight = 42.sp
            )
            CategoryTabRow()
        }
    }
}

@Composable
fun CategoryProductList() {

}

@Composable
fun CategoryProductCard(name: String, price: String, image: String) {

    Surface(modifier = Modifier
        .padding(top = 16.dp)
        .background(FieldColor, shape = RoundedCornerShape(24.dp))
        .fillMaxWidth()
        .height(150.dp)
        .clip(RoundedCornerShape(24.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(FieldColor),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .padding(8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = name,
                    color = Color(0xFF152354),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.padding(horizontal = 16.dp))
                Text(
                    text = price,
                    color = Color(0xFF152354),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(8.dp))

                ShoeAppButton(
                    modifier = Modifier
                        .width(77.dp)
                        .height(33.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "View",
                        fontSize = 10.sp
                    )
                }

            }

//            Image(
//                painter = painterResource(id = ""),
//                contentDescription = "Image of sneaker",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier.size(100.dp)
//            )

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = name,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(100.dp)
            )

        }
    }



}

@Preview
@Composable
fun CategoryPreviewScreen() {
    CategoryScreen(rememberNavController())
}