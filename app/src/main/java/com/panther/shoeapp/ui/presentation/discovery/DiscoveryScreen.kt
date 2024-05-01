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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.R
import com.panther.shoeapp.ui.component.HomeScreenCard
import com.panther.shoeapp.ui.component.NavDrawer
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.presentation.home.HomeViewModel
import com.panther.shoeapp.ui.theme.navyBlue
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun DiscoveryScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val allShoesState by viewModel.allShoes.collectAsState()
    val itemCount by viewModel.itemCount.collectAsState()

    LaunchedEffect(key1 = allShoesState){
        coroutineScope {
            launch {
                viewModel.getItemCount()
            }
        }
    }

    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {
            NavDrawer(route = String(), modifier = Modifier, navHostController)
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
                        append("$itemCount Shoes Are \n")
                        pushStyle(style = SpanStyle(fontWeight = FontWeight.Bold))
                        append("Available")
                    },
                    color = navyBlue,
                    fontSize = 36.sp,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(16.dp),
                    lineHeight = 36.sp
                )

                val shoeList = allShoesState.data ?: emptyList()

                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {

                    items(shoeList) { product ->

                        HomeScreenCard(
                            product.name.toString(),
                            product.price,
                            product.images!!.first().toString(),
                            product.id!!,
                            navHostController = navHostController
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewDiscoveryScreen() {
    DiscoveryScreen(navHostController = rememberNavController())
}