package com.panther.shoeapp.ui.presentation.discovery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.panther.shoeapp.R
import com.panther.shoeapp.models.data.Product
import com.panther.shoeapp.models.products
import com.panther.shoeapp.ui.component.BottomNav
import com.panther.shoeapp.ui.component.NavDrawer
import com.panther.shoeapp.ui.component.ProductCard
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.navyBlue
import kotlinx.coroutines.launch

@Composable
fun DiscoveryScreen(
    navHostController: NavHostController
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {
            NavDrawer(route = String())
        }

    ) {

        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "DISCOVER ALL",
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
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.apps_rectangle),
                                    contentDescription = "navigation icon",
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

            },
            bottomBar = {
                BottomNav(navController = navHostController)
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
                        append("1001 Shoes Are \n\n")
                        pushStyle(style = SpanStyle(fontWeight = FontWeight.Bold))
                        append("Available")
                    },
                    color = navyBlue,
                    fontSize = 36.sp,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(16.dp)
                )

                AllProductsList(productList = products)

            }
        }
    }
}

@Composable
fun AllProductsList(productList: List<Product>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {

        items(productList) { product ->

            ProductCard(product.name, product.price, product.image)
        }
    }
}

@Preview
@Composable
fun PreviewDiscoveryScreen() {
    DiscoveryScreen(navHostController = rememberNavController())
}