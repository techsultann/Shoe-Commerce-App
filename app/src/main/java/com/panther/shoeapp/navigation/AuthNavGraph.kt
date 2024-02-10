package com.panther.shoeapp.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.auth.LoginScreen
import com.panther.shoeapp.ui.presentation.auth.OnboardingViewModel
import com.panther.shoeapp.ui.presentation.auth.SignupScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {

    navigation(
        startDestination = AuthScreen.OnboardScreen.route,
        route = Graph.AUTHENTICATION
    ) {
        composable(route = AuthScreen.SignupScreen.route) {
            val vm = hiltViewModel<OnboardingViewModel>()
            SignupScreen(navController = navController, vm)
        }
        composable(route = AuthScreen.LoginScreen.route) {
            val vm = hiltViewModel<OnboardingViewModel>()
            LoginScreen(navController = navController, vm)
        }

    }
}

sealed class AuthScreen(val route: String) {
    object OnboardScreen : AuthScreen(route = "onboard_screen")
    object SignupScreen : AuthScreen(route = "signup_screen")
    object LoginScreen : AuthScreen (route = "login_screen")
}