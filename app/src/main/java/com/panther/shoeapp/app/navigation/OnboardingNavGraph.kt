package com.panther.shoeapp.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.auth.LoginScreen
import com.panther.shoeapp.ui.presentation.auth.SignupScreen
import com.panther.shoeapp.utils.ON_BOARDING_ROUTE
import com.panther.shoeapp.utils.Screen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController
) {

    navigation(
        startDestination = Screen.OnboardScreen.route,
        route = ON_BOARDING_ROUTE
    ) {
        composable(route = Screen.SignupScreen.route) {
            SignupScreen(navController = navController)
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

    }
}