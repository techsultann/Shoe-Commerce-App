package com.panther.shoeapp.navigation

import android.content.Intent
import android.util.Log
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
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.panther.shoeapp.presentation.address.AddAddress
import com.panther.shoeapp.presentation.address.AddressList
import com.panther.shoeapp.presentation.address.AddressViewModel
import com.panther.shoeapp.presentation.address.EditAddress
import com.panther.shoeapp.presentation.auth.LoginScreen
import com.panther.shoeapp.presentation.auth.OnboardingViewModel
import com.panther.shoeapp.presentation.auth.SignupScreen
import com.panther.shoeapp.presentation.brand.BrandScreen
import com.panther.shoeapp.presentation.brand.BrandViewModel
import com.panther.shoeapp.presentation.card.AddCardScreen
import com.panther.shoeapp.presentation.card.CardScreen
import com.panther.shoeapp.presentation.card.CreditCardVieModel
import com.panther.shoeapp.presentation.cart.CartScreen
import com.panther.shoeapp.presentation.cart.CartViewModel
import com.panther.shoeapp.presentation.category.CategoryScreen
import com.panther.shoeapp.presentation.category.CategoryViewModel
import com.panther.shoeapp.presentation.checkout.CheckOutScreen
import com.panther.shoeapp.presentation.checkout.CheckoutViewModel
import com.panther.shoeapp.presentation.checkout.OrderSuccessfulScreen
import com.panther.shoeapp.presentation.details.DetailsScreen
import com.panther.shoeapp.presentation.details.DetailsViewModel
import com.panther.shoeapp.presentation.discovery.DiscoveryScreen
import com.panther.shoeapp.presentation.home.HomeScreenContent
import com.panther.shoeapp.presentation.home.HomeViewModel
import com.panther.shoeapp.presentation.order.OrderSummaryScreen
import com.panther.shoeapp.presentation.order.OrderViewModel
import com.panther.shoeapp.presentation.order.OrdersScreen
import com.panther.shoeapp.presentation.profile.ProfileScreen
import com.panther.shoeapp.presentation.profile.ProfileViewModel
import com.panther.shoeapp.presentation.review.ReviewScreen
import com.panther.shoeapp.presentation.review.ReviewViewModel
import com.panther.shoeapp.presentation.settings.SettingsScreen


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
            ProfileScreen(viewModel, navController)
        }

        navDrawerGraph(navController)
        detailsNavGraph(navController)
        cartNavGraph(navController)
        profileNavGraph(navController)

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
            DetailsScreen(shoeId = shoeId, viewModel, navController)
        }
    }
}

fun NavGraphBuilder.profileNavGraph(navController: NavHostController){
    navigation(
        route = Graph.PROFILE,
        startDestination = BottomBarScreen.Profile.route
    ){

        composable(
            route = HomeScreenNav.AddressListScreen.route,
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
            val viewModel = hiltViewModel<AddressViewModel>()
            AddressList(navHostController = navController)
        }

        composable(
            route = HomeScreenNav.AddAddressScreen.route,
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
            val viewModel = hiltViewModel<AddressViewModel>()
            AddAddress(navHostController = navController)
        }

        composable(
            route = "${ HomeScreenNav.EditAddressScreen.route }/{addressId}",
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
            val addressId = backStackEntry.arguments?.getString("addressId")
            val viewModel = hiltViewModel<AddressViewModel>()
            Log.d("ADDRESS ID", "Address ID: $addressId")
            EditAddress(navHostController = navController, addressId = addressId, viewModel)
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
            CheckOutScreen(navController, subTotalPrice, viewModel)
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
            },
            deepLinks = listOf(
                navDeepLink {
                uriPattern = "https://techsultan.page.link/payout/{status}/{tx_ref}/{transaction_id}"
                action = Intent.ACTION_VIEW
            }
            ),
            arguments = listOf(
                navArgument("status") { type = NavType.StringType },
                navArgument("tx_ref") { type = NavType.StringType },
                navArgument("transaction_id") { type = NavType.IntType }
            )
        ){ backStackEntry ->

            val status = backStackEntry.arguments?.getString("status")
            val txRef = backStackEntry.arguments?.getString("tx_ref")
            val transactionId = backStackEntry.arguments?.getString("transaction_id")

            val viewModel = hiltViewModel<CheckoutViewModel>()
            OrderSuccessfulScreen(navController, viewModel, status, txRef, transactionId)
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
            val viewModel = hiltViewModel<CategoryViewModel>()
            CategoryScreen(navController, viewModel)
        }

        composable(route = HomeScreenNav.TrackOrder.route){
            val viewModel = hiltViewModel<OrderViewModel>()
            OrdersScreen(viewModel, navController)
        }

        composable(
            route = "${ HomeScreenNav.OrderSummaryScreen.route }/{orderId}/{itemName}/{itemPrice}/{itemImage}/{orderDate}/{cartItemId}",
            arguments = listOf(
                navArgument("orderId") {type = NavType.StringType},
                navArgument("itemName") {type = NavType.StringType},
                navArgument("itemPrice") {type = NavType.StringType},
                navArgument("itemImage") {type = NavType.StringType},
                navArgument("orderDate") {type = NavType.StringType},
                navArgument("cartItemId") {type = NavType.StringType}
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
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")
            val itemName = backStackEntry.arguments?.getString("itemName")
            val itemPrice = backStackEntry.arguments?.getString("itemPrice")
            val itemImage = backStackEntry.arguments?.getString("itemImage")
            val orderDate = backStackEntry.arguments?.getString("orderDate")
            val cartItemId = backStackEntry.arguments?.getString("cartItemId")
            val viewModel = hiltViewModel<OrderViewModel>()

            OrderSummaryScreen(
                viewModel,
                navHostController = navController,
                orderId = orderId,
                itemName = itemName,
                itemPrice = itemPrice,
                itemImage = itemImage,
                orderDate = orderDate,
                cartItemId = cartItemId
            )
        }

        composable(
            route = "${HomeScreenNav.ReviewScreen.route}/{itemImage}/{cartItemId}",
            arguments = listOf(
                navArgument("itemImage") {type = NavType.StringType},
                navArgument("cartItemId") {type = NavType.StringType}
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
        ) { backStackEntry ->

            val cartItemId = backStackEntry.arguments?.getString("cartItemId")
            val itemImage = backStackEntry.arguments?.getString("itemImage")
            val viewModel = hiltViewModel<ReviewViewModel>()

           ReviewScreen(viewModel = viewModel, navHostController = navController, cartItemId, itemImage)
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
            val cvv = backStackEntry.arguments?.getString("cardNumber")
            CardScreen(cardType, name, cardNumber, cvv, navController)
        }
        composable(
            route = HomeScreenNav.AddCardScreen.route,
        ){
            val viewModel = hiltViewModel<CreditCardVieModel>()
            AddCardScreen(navController, viewModel)
        }
        composable(route = HomeScreenNav.SettingsScreen.route){
            SettingsScreen()
        }
        composable(route = HomeScreenNav.BrandScreen.route){
            val viewModel = hiltViewModel<BrandViewModel>()
            BrandScreen(navHostController = navController, viewModel)
        }
        composable(route = Graph.AUTHENTICATION){
            val vm = hiltViewModel<OnboardingViewModel>()
            LoginScreen(navController = navController, vm)
        }
        composable(
            route = AuthScreen.SignupScreen.route,
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
        ) {
            val vm = hiltViewModel<OnboardingViewModel>()
            SignupScreen(navController = navController, vm)
        }

        composable(
            route = Graph.AUTHENTICATION,
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
        ) {
            val vm = hiltViewModel<OnboardingViewModel>()
            LoginScreen(navController = navController, vm)
        }
    }
}



sealed class HomeScreenNav(val route: String) {
    object DetailsScreen : HomeScreenNav(route = "details_screen")
    object CategoryScreen : HomeScreenNav(route = "category_screen")
    object BrandScreen : HomeScreenNav(route = "brand_screen")
    object PaymentCardScreen : HomeScreenNav(route = "card_screen")
    object AddCardScreen : HomeScreenNav(route = "add_card_screen")
    object SettingsScreen : HomeScreenNav(route = "settings_screen")
    object CheckoutScreen : HomeScreenNav(route = "checkout_screen")
    object SuccessfulScreen : HomeScreenNav(route = "successful_screen")
    object TrackOrder : HomeScreenNav(route = "track_order_screen")
    object OrderSummaryScreen : HomeScreenNav(route = "order_summary_screen")
    object AddAddressScreen : HomeScreenNav(route = "add_address_screen")
    object EditAddressScreen : HomeScreenNav(route = "edit_address_screen")
    object AddressListScreen : HomeScreenNav(route = "address_list_screen")
    object ReviewScreen : HomeScreenNav(route = "review_screen")
}