package com.panther.shoeapp.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.auth.LoginScreen
import com.panther.shoeapp.ui.presentation.auth.OnboardingViewModel
import com.panther.shoeapp.ui.presentation.card.AddCardScreen
import com.panther.shoeapp.ui.presentation.card.CardScreen
import com.panther.shoeapp.ui.presentation.cart.CartScreen
import com.panther.shoeapp.ui.presentation.cart.CartViewModel
import com.panther.shoeapp.ui.presentation.category.CategoryScreen
import com.panther.shoeapp.ui.presentation.checkout.CheckOutScreen
import com.panther.shoeapp.ui.presentation.checkout.CheckoutViewModel
import com.panther.shoeapp.ui.presentation.checkout.OrderSuccessfulScreen
import com.panther.shoeapp.ui.presentation.details.DetailsScreen
import com.panther.shoeapp.ui.presentation.details.DetailsViewModel
import com.panther.shoeapp.ui.presentation.discovery.DiscoveryScreen
import com.panther.shoeapp.ui.presentation.home.HomeScreenContent
import com.panther.shoeapp.ui.presentation.home.HomeViewModel
import com.panther.shoeapp.ui.presentation.profile.ProfileScreen
import com.panther.shoeapp.ui.presentation.profile.ProfileViewModel
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
        composable(route = BottomBarScreen.Profile.route){
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(viewModel)
        }
        navDrawerGraph(navController)
        detailsNavGraph(navController)
        cartNavGraph(navController)

    }

}
fun NavGraphBuilder.detailsNavGraph(navController: NavHostController){
    navigation(
        route = Graph.DETAILS,
        startDestination = "${HomeScreenNav.DetailsScreen.route}/{shoeId}"
    ){
        composable(
            route = "${HomeScreenNav.DetailsScreen.route}/{shoeId}",
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){ backStackEntry ->
            val viewModel = hiltViewModel<DetailsViewModel>()
            val shoeId = backStackEntry.arguments?.getString("shoeId")
            DetailsScreen(shoeId = shoeId, viewModel)
        }
    }
}

fun NavGraphBuilder.cartNavGraph(navController: NavHostController){
    navigation(
        route = Graph.CHECKOUT,
        startDestination = "${HomeScreenNav.CheckoutScreen.route}/{subTotalPrice}"
    ){
        composable(
            route = "${HomeScreenNav.CheckoutScreen.route}/{subTotalPrice}",
            arguments = listOf(navArgument("subTotalPrice") {type = NavType.StringType} ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){ backStackEntry ->

            val viewModel = hiltViewModel<CheckoutViewModel>()
            val subTotalPrice = backStackEntry.arguments?.getString("subTotalPrice")
            CheckOutScreen(navController, subTotalPrice)
        }
        composable(
            route = HomeScreenNav.SuccessfulScreen.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){
            OrderSuccessfulScreen(navController)
        }
        composable(route = BottomBarScreen.Home.route){
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreenContent(navController, viewModel)
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
        composable(
            route = "${ HomeScreenNav.PaymentCardScreen.route }/{cardType}/{name}/{cardNumber}",
            arguments = listOf(
                navArgument("cardType") {type = NavType.StringType},
                navArgument("name") {type = NavType.StringType},
                navArgument("cardNumber") {type = NavType.StringType}
            ),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ){ backStackEntry ->
            val cardType = backStackEntry.arguments?.getString("cardType")
            val name = backStackEntry.arguments?.getString("name")
            val cardNumber = backStackEntry.arguments?.getString("cardNumber")
            CardScreen(cardType, name, cardNumber, navController)
        }
        composable(
            route = HomeScreenNav.AddCardScreen.route,
        ){
            AddCardScreen(navController)
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
    object AddCardScreen : HomeScreenNav(route = "add_card_screen")
    object SettingsScreen : HomeScreenNav(route = "settings_screen")
    object DiscoversScreen : HomeScreenNav(route = "discover_screen")
    object CheckoutScreen : HomeScreenNav(route = "checkout_screen")
    object SuccessfulScreen : HomeScreenNav(route = "successful_screen")
}