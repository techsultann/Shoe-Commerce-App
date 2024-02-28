package com.panther.shoeapp.ui.presentation.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.presentation.home.HomeViewModel
import com.panther.shoeapp.ui.theme.Red
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.skyBlue
import com.panther.shoeapp.ui.theme.white
import com.panther.shoeapp.ui.theme.yellow

@Composable
fun DetailsScreen(
    shoeId: String?,
    viewModel: HomeViewModel = viewModel()
) {
    val shoeResource by viewModel.shoeById.collectAsState()
    val shoeDetails = shoeResource.data

    if (shoeId != null) {
            viewModel.getShoeById(shoeId)
        }
//    LaunchedEffect(key1 = shoeId) {
//
//        if (shoeId != null) {
//            viewModel.getShoeById(shoeId)
//        }
//    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DETAILS",
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
                            onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "navigation back icon",
                                tint = Color.Unspecified
                            )
                        }

                    }
                },
                actions = {
                    Surface(
                        modifier = Modifier.size(height = 44.dp, width = 84.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(6.dp)
                        ) {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.light),
                                    contentDescription = "light theme icon",
                                    tint = Color.Unspecified)

                            }
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.half__moon),
                                    contentDescription = "dark theme icon",
                                    tint = Color.Unspecified
                                )

                            }
                        }

                    }
                }
            )
        }
    ) { padding ->

        Column(modifier = Modifier
            .padding(paddingValues = padding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {

            ShoeImagePager()


            if (shoeDetails != null) {
                Text(
                    text = shoeDetails.name!!,
                    color = navyBlue,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            if (shoeDetails != null) {
                Text(
                    text = shoeDetails.description!!,
                    fontSize = 18.sp,
                    color = navyBlue,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                ) {

                    Text(
                        text = buildAnnotatedString {
                            append("Price: ")
                            pushStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold))
                            if (shoeDetails != null) {
                                append("N${shoeDetails.price.toString()}")
                            }
                        },
                        color = navyBlue,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("Brand: ")
                            pushStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold))
                            if (shoeDetails != null) {
                                append(shoeDetails.brand)
                            }
                        },
                        color = navyBlue,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("Sizes: ")
                            pushStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold))
                            append("Rubber 100%")
                        },
                        color = navyBlue,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                        overflow = TextOverflow.Ellipsis
                    )

                }


                Box(modifier = Modifier
                    .width(99.dp)
                    .height(157.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(skyBlue)
                    .clickable {  },
                    contentAlignment = Alignment.Center

                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cart),
                            contentDescription = "cart icon")
                        Spacer(modifier = Modifier.padding(vertical = 12.dp))
                        if (shoeDetails != null) {
                            Text(
                                text = "N${shoeDetails.price.toString()}",
                                color = white,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                }
            }


        }
    }



}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoeImagePager(
    viewModel: HomeViewModel = viewModel()
) {
    val shoeResource by viewModel.shoeById.collectAsState()
    val shoeDetails = shoeResource.data
    val imageCount = shoeDetails?.images ?: emptyList()
    val pagerState = rememberPagerState {
        imageCount.size
    }

    HorizontalPager(state = pagerState) { page ->
        val imageUrl = imageCount[page]

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Shoe image",
            contentScale = ContentScale.Fit ,
            modifier = Modifier
                .padding(vertical = 50.dp)
                .fillMaxWidth()
                .height(200.dp),
            alignment = Alignment.Center
        )

    }
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) yellow else Red
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(16.dp)
            )
        }
    }

}

@Composable
fun DetailsText() {

    Row(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxWidth()
        .padding(16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(modifier = Modifier) {

            Text(
                text = "Structure",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = navyBlue
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

            Text(
                text = buildAnnotatedString {
                    append("Price: ")
                    pushStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold))
                    append("Rubber 100%")
                },
                color = navyBlue,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = buildAnnotatedString {
                    append("Brand: ")
                    pushStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold))
                    append("Rubber 100%")
                },
                color = navyBlue,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = buildAnnotatedString {
                    append("Sizes: ")
                    pushStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold))
                    append("Rubber 100%")
                },
                color = navyBlue,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = buildAnnotatedString {
                    append("Colors: ")
                    pushStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold))
                    append("Rubber 100%")
                },
                color = navyBlue,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Colors",
                fontSize = 16.sp,
                color = skyBlue
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Box(modifier = Modifier
            .width(99.dp)
            .height(157.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(skyBlue),
            contentAlignment = Alignment.Center

        ) {

            Column {
                Image(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = "cart icon")
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Text(
                    text = "$ 650",
                    color = white,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
    }



}


