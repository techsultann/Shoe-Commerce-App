@file:OptIn(ExperimentalFoundationApi::class)

package com.panther.shoeapp.ui.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.panther.shoeapp.ui.component.FancyIndicator
import com.panther.shoeapp.ui.component.ProductCard
import com.panther.shoeapp.ui.theme.FieldColor
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.skyBlue
import com.panther.shoeapp.ui.theme.white
import com.panther.shoeapp.utils.Resource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeTabRow(
    navHostController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {
    //val shoeResource by viewModel.allShoes.collectAsState()
    val titles = listOf("All", "Nike", "Adidas", "Puma")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState {
        titles.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = navyBlue,
            indicator = @Composable { tabPositions: List<TabPosition> ->
                FancyIndicator(
                    color = MaterialTheme.colorScheme.background,
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .clip(RoundedCornerShape(50.dp))
                )
            }
        ) {
            titles.forEachIndexed { index, title ->

                Tab(
                    selected = pagerState.currentPage == index ,//index == selectedTabIndex ,
                    onClick = {
                        selectedTabIndex = index
                              },
                    text = {
                        if (index == selectedTabIndex) {
                            Text(
                                title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = white
                            )
                        } else {
                            Text(
                                title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = navyBlue
                            )
                        }
                           },
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(CircleShape)
                        .background(
                            color = if (index == selectedTabIndex) skyBlue
                            else FieldColor
                        )
                )
            }
        }


        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                val shoeResource = when(selectedTabIndex) {
                    0 -> viewModel.allShoes.collectAsState()
                    1 -> viewModel.nike.collectAsState()
                    2 -> viewModel.adidas.collectAsState()
                    3 -> viewModel.puma.collectAsState()
                    else -> {viewModel.allShoes.collectAsState()}
                }

                when (val resource = shoeResource.value) {

                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                        val shoeList = resource.data ?: emptyList()

                        if (shoeList.isEmpty()) {
                            Text(
                                text = "No product available at the moment, Check back later...",
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding()
                                    .align(Alignment.Center)
                            )
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(count = 2),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(16.dp)
                            ) {

                                items(
                                    items = shoeList,
                                    key = {
                                        it.id!!
                                    }
                                ) { product ->

                                    ProductCard(
                                        product.name!!,
                                        product.price!!,
                                        product.images?.first().toString(),
                                        product.id!!,
                                        navHostController
                                    )
                                }
                            }
                        }

                    }
                    is Resource.Error -> {

                    }
                }

            }
        }
    }


    }


@Preview
@Composable
fun TabRowPreview() {
    HomeTabRow(rememberNavController())
}