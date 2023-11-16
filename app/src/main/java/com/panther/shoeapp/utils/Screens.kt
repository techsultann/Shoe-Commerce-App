package com.panther.shoeapp.utils



const val ON_BOARDING_ROUTE = "onboarding"
const val ROOT_ROUTE = "root"
const val HOME_ROUTE = "home"
const val BOTTOM_NAV_ROUTE = "bottom_nav"
sealed class Screen(val route: String) {

    object OnboardScreen : Screen(route = "onboard_screen")
    object HomeScreen : Screen(route = "home_screen")
    object SignupScreen : Screen(route = "signup_screen")
    object LoginScreen : Screen (route = "login_screen")
    object DetailsScreen : Screen(route = "details_screen")
    object DiscoveryScreen : Screen(route = "discovery_screen")
}