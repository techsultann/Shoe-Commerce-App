package com.panther.shoeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.auth.LoginScreen
import com.panther.shoeapp.ui.presentation.auth.OnboardingViewModel
import com.panther.shoeapp.ui.presentation.card.CardScreen
import com.panther.shoeapp.ui.presentation.cart.CartScreen
import com.panther.shoeapp.ui.presentation.cart.CartViewModel
import com.panther.shoeapp.ui.presentation.category.CategoryScreen
import com.panther.shoeapp.ui.presentation.details.DetailsScreen
import com.panther.shoeapp.ui.presentation.details.DetailsViewModel
import com.panther.shoeapp.ui.presentation.discovery.DiscoveryScreen
import com.panther.shoeapp.ui.presentation.home.HomeScreenContent
import com.panther.shoeapp.ui.presentation.home.HomeViewModel
import com.panther.shoeapp.ui.presentation.profile.ProfileScreen
import com.panther.shoeapp.ui.presentation.settings.SettingsScreen


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
            val viewModel = hiltViewModel<HomeViewModel>()
            DiscoveryScreen(navController, viewModel)
        }
        composable(route = BottomBarScreen.Cart.route){
            val viewModel = hiltViewModel<CartViewModel>()
            CartScreen(navHostController = navController)
        }
        composable(route = BottomBarScreen.Message.route){
            //MessageScreen()
        }
        composable(route = BottomBarScreen.Profile.route){
            ProfileScreen()
        }
        navDrawerGraph(navController)
        detailsNavGraph(navController)

    }

}
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController){
    navigation(
        route = Graph.DETAILS,
        startDestination = "${HomeScreenNav.DetailsScreen.route}/{shoeId}"
    ){
        composable(route = "${HomeScreenNav.DetailsScreen.route}/{shoeId}"){ backStackEntry ->
            val viewModel = hiltViewModel<DetailsViewModel>()
            val shoeId = backStackEntry.arguments?.getString("shoeId")
            DetailsScreen(shoeId = shoeId, viewModel)
        }
    }
}
fun NavGraphBuilder.navDrawerGraph(navController: NavHostController){
    navigation(
        route = Graph.NAV_DRAWER,
        startDestination = HomeScreenNav.CategoryScreen.route
    ){
        composable(route = HomeScreenNav.CategoryScreen.route){
            CategoryScreen(navController)
        }
        composable(route = HomeScreenNav.PaymentCardScreen.route){
            CardScreen()
        }
        composable(route = HomeScreenNav.SettingsScreen.route){
            SettingsScreen()
        }
        composable(route = HomeScreenNav.DiscoversScreen.route){
            DiscoveryScreen(navHostController = navController)
        }
        composable(route = AuthScreen.LoginScreen.route){
            val vm = hiltViewModel<OnboardingViewModel>()
            LoginScreen(navController = navController, vm)
        }
    }
}



sealed class HomeScreenNav(val route: String) {
    object DetailsScreen : HomeScreenNav(route = "details_screen")
    object CategoryScreen : HomeScreenNav(route = "category_screen")
    object PaymentCardScreen : HomeScreenNav(route = "card_screen")
    object SettingsScreen : HomeScreenNav(route = "settings_screen")
    object DiscoversScreen : HomeScreenNav(route = "discover_screen")
}