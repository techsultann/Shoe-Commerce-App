package com.panther.shoeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.details.DetailsScreen
import com.panther.shoeapp.ui.presentation.discovery.DiscoveryScreen
import com.panther.shoeapp.ui.presentation.home.HomeScreenContent
import com.panther.shoeapp.ui.presentation.home.HomeViewModel


@Composable
fun HomeNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
        route = Graph.HOME
    ){
        composable(route = BottomBarScreen.Home.route){
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreenContent(navController, viewModel)
        }
        composable(route = BottomBarScreen.Discovery.route){
            DiscoveryScreen(navHostController = navController)
        }
        composable(route = BottomBarScreen.Favorite.route){

        }
        composable(route = BottomBarScreen.Message.route){
            //MessageScreen()
        }
        composable(route = BottomBarScreen.Profile.route){

        }

    }

}
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController){
    navigation(
        route = Graph.DETAILS,
        startDestination = HomeScreenNav.DetailsScreen.route
    ){
        composable(route = HomeScreenNav.DetailsScreen.route){
            DetailsScreen()
        }
    }
}


sealed class HomeScreenNav(val route: String) {
    object DetailsScreen : HomeScreenNav(route = "details_screen")
    object CategoryScreen : HomeScreenNav(route = "category_screen")
}