package com.panther.shoeapp.ui.presentation.home


import android.util.Log
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.panther.shoeapp.navigation.HomeScreenNav
import com.panther.shoeapp.ui.component.HomeScreenCard
import com.panther.shoeapp.ui.component.NavDrawer
import com.panther.shoeapp.ui.component.PriceRangeSlider
import com.panther.shoeapp.ui.component.ShoeAppButton
import com.panther.shoeapp.ui.component.TopAppBar
import com.panther.shoeapp.ui.theme.navyBlue
import com.panther.shoeapp.ui.theme.secondaryTextColor
import com.panther.shoeapp.ui.theme.yellow
import com.panther.shoeapp.utils.Util
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    navHostController: NavHostController,
    viewModel: HomeViewModel = viewModel()
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
   // val getAllShoeState by viewModel.allShoes.collectAsState()
    ModalNavigationDrawer(

        drawerState = drawerState,

        drawerContent = {
            NavDrawer(route = String(), modifier = Modifier, navHostController)
        }

    ) {

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(paddingValues = padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                SectionA()

                NewDeals(navHostController)

                Nike(navHostController)

                Adidas(navHostController)

                Puma(navHostController)

                Spacer(modifier = Modifier.height(100.dp))

            }

        }
    }
}



@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SectionA() {


    val count = Util.Banners.size
    val pagerState = rememberPagerState { count }
    var index: Int by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LaunchedEffect(Unit) {
                while (isActive) {
                    delay(3000)
                    pagerState.animateScrollToPage(index)
                    index = (index + 1) % count
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) { index ->

                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(180.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 6.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                verticalArrangement = Arrangement.SpaceAround
                            ) {

                                Text(
                                    text = Util.Banners[index].title,
                                    color = navyBlue,
                                    fontSize = 22.sp,
                                    textAlign = TextAlign.Start,
                                    overflow = TextOverflow.Clip,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                )

                                ShoeAppButton(
                                    onClick = { /*TODO*/ },
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Text(
                                        text = Util.Banners[index].btnText
                                    )
                                }
                            }

                            Image(
                                painter = painterResource(id = Util.Banners[index].image),
                                contentDescription = "image",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .size(100.dp),
                                contentScale = ContentScale.Fit
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
                                val color = if (pagerState.currentPage == iteration) yellow else secondaryTextColor
                                Box(
                                    modifier = Modifier
                                        .padding(3.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(8.dp)
                                )
                            }
                        }
                    }




                }

            }


        }


    }


}

@Composable
fun NewDeals(
    navHostController: NavHostController
) {

    val viewModel: HomeViewModel = viewModel()
    val newDeals = viewModel.newDeals.collectAsState()

    Column() {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Hot Deals")

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "View All",
                modifier = Modifier
                    .clickable { navHostController.navigate(HomeScreenNav.BrandScreen.route) }
            )
        }

        val newDealsList = newDeals.value.data ?: emptyList()
        Log.d("New DEAL LIST", "NEW DEALS: $newDealsList")

        LazyRow(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {

            items(newDealsList) { newDeal ->

                HomeScreenCard(
                    name = newDeal.name,
                    price = newDeal.price,
                    image = newDeal.images!!.first(),
                    shoeId = newDeal.id,
                    navHostController = navHostController)
            }

        }
    }
}

@Composable
fun Nike(
    navHostController: NavHostController
) {


    val viewModel: HomeViewModel = viewModel()
    val nike = viewModel.nike.collectAsState()

    Column() {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Nike"
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "View All",
                modifier = Modifier
                    .clickable { navHostController.navigate(HomeScreenNav.BrandScreen.route) }
            )
        }

        val nikeList = nike.value.data ?: emptyList()

        LazyRow(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {

            items(nikeList) { nike ->

                HomeScreenCard(
                    name = nike.name,
                    price = nike.price,
                    image = nike.images!!.first(),
                    shoeId = nike.id,
                    navHostController = navHostController)
            }

        }
    }
}

@Composable
fun Adidas(
    navHostController: NavHostController
) {

    val viewModel: HomeViewModel = viewModel()
    val adidas = viewModel.adidas.collectAsState()

    when {
        adidas.value.data?.isEmpty() == true -> {

        }
        else -> {

            Column() {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "Adidas")

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "View All",
                        modifier = Modifier
                            .clickable { navHostController.navigate(HomeScreenNav.BrandScreen.route) }
                    )
                }

                val adidasList = adidas.value.data ?: emptyList()

                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {

                    items(adidasList) { adidas ->

                        HomeScreenCard(
                            name = adidas.name,
                            price = adidas.price,
                            image = adidas.images!!.first(),
                            shoeId = adidas.id,
                            navHostController = navHostController)
                    }

                }
            }
        }
    }


}

@Composable
fun Puma(
    navHostController: NavHostController
) {

    val viewModel: HomeViewModel = viewModel()
    val puma = viewModel.puma.collectAsState()

    Column() {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(text = "Puma")

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "View All",
                modifier = Modifier
                    .clickable { navHostController.navigate(HomeScreenNav.BrandScreen.route) }
            )
        }

        val pumaList = puma.value.data ?: emptyList()

        LazyRow(
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {

            items(pumaList) { puma ->

                HomeScreenCard(
                    name = puma.name,
                    price = puma.price,
                    image = puma.images!!.first(),
                    shoeId = puma.id,
                    navHostController = navHostController)
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerCard(
    title: String
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = title,
                color = navyBlue,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Visible,
                lineHeight = 46.sp,
                modifier = Modifier
                    .width(200.dp)
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
}


@Preview
@Composable
fun MainScreenPreview() {

    HomeScreenContent(
        navHostController = rememberNavController()
    )
}