package com.panther.shoeapp.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.auth.LoginScreen
import com.panther.shoeapp.ui.presentation.auth.OnboardingViewModel
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
            val vm = hiltViewModel<OnboardingViewModel>()
            SignupScreen(navController = navController, vm)
        }
        composable(route = Screen.LoginScreen.route) {
            val vm = hiltViewModel<OnboardingViewModel>()
            LoginScreen(navController = navController, vm)
        }

    }
}