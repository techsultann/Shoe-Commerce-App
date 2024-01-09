package com.panther.shoeapp.ui.presentation.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.panther.shoeapp.ui.component.PriceRangeSlider
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.navyBlue
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navHostController: NavHostController) {

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
                            text = "Home",
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
                                onClick = { scope.launch {
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
            },

            bottomBar = {
                BottomNav(navController = navHostController)
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(paddingValues = padding)
                    .fillMaxSize()
            ) {
                // Spacer(modifier = Modifier.padding(16.dp))
                SectionA()

                // Spacer(modifier = Modifier.padding(16.dp))
                SectionB()

                SectionC(productList = products)

            }

        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectionA() {

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 16.dp)
    ) {

        Text(
            text = buildAnnotatedString { 
                append("Enjoy New Nike\n\n")
                pushStyle(style = SpanStyle(fontWeight = FontWeight.Bold))
                append("Products")
            },
            color = navyBlue,
            fontSize = 36.sp,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

        Surface(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .width(74.dp)
                .height(66.dp)
                .clip(
                    shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                ),
            color = Color(0xFFFF5545)
        ) {

            IconButton(onClick = { showBottomSheet = true }) {
                Icon(painter = painterResource(
                    id = R.drawable.filter_8),
                    contentDescription = "filter icon",
                    tint = Color.Unspecified
                )

            }
        }

        if (showBottomSheet){

            ModalBottomSheet(
                modifier = Modifier,
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                PriceRangeSlider(modifier = Modifier)

                Spacer(modifier = Modifier.padding(50.dp))

                ShoeAppButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .requiredHeight(66.dp),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false

                            }
                        }
                    }
                ) {
                    Text(
                        text = "Apply",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SectionB() {

   LazyRow(
       modifier = Modifier.padding(16.dp),
       horizontalArrangement = Arrangement.SpaceEvenly
   ) {

       item {

           ShoeAppButton(
               onClick = { /*TODO*/ },
               modifier = Modifier
                   .size(width = 75.dp, height = 68.dp)
                   .clip(shape = RoundedCornerShape(22.dp)) ,
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xFFF8F8FA)
               )
           ) {
               Text(
                   text = "All",
                   color = Color(0xFF152354)
               )

           }
           Spacer(modifier = Modifier.padding(8.dp) )

           ShoeAppButton(
               onClick = { /*TODO*/ },
               modifier = Modifier
                   .size(width = 100.dp, height = 68.dp)
                   .clip(shape = RoundedCornerShape(22.dp)) ,
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xFF69BCFC)
               )
           ) {
               IconButton(onClick = { /*TODO*/ }) {
                   Icon(
                       painter = painterResource(id = R.drawable.nike) ,
                       contentDescription = "nike logo")
                   
               }

           }
           Spacer(modifier = Modifier.padding(8.dp) )

           ShoeAppButton(
               onClick = { /*TODO*/ },
               modifier = Modifier
                   .size(width = 75.dp, height = 68.dp)
                   .clip(shape = RoundedCornerShape(22.dp)) ,
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xFFF8F8FA)
               )
           ) {
               IconButton(onClick = { /*TODO*/ }) {
                   Icon(
                       painter = painterResource(id = R.drawable.adidas) ,
                       contentDescription = "adidas logo",
                       tint = Color.Unspecified)

               }

           }

           Spacer(modifier = Modifier.padding(8.dp) )

           ShoeAppButton(
               onClick = { /*TODO*/ },
               modifier = Modifier
                   .size(width = 75.dp, height = 68.dp)
                   .clip(shape = RoundedCornerShape(22.dp)) ,
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xFFF8F8FA)
               )
           ) {
               IconButton(
                   onClick = { /*TODO*/ },
               ) {
                   Icon(
                       painter = painterResource(id = R.drawable.puma) ,
                       contentDescription = "puma logo",
                       tint = Color.Unspecified
                   )

               }

           }
       }
   }
}

@Composable
fun SectionC(productList: List<Product>) {
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
fun MainScreenPreview() {

    HomeScreen(
        navHostController = rememberNavController()
    )
}


@Preview
@Composable
fun SectionBPreview() {
    SectionB()
}

@Preview
@Composable
fun SectionAPreview() {
    SectionA()
}