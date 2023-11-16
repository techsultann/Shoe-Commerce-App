package com.panther.shoeapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.panther.shoeapp.ui.presentation.onboard.OnboardScreen
import com.panther.shoeapp.utils.ROOT_ROUTE
import com.panther.shoeapp.utils.Screen

@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.OnboardScreen.route,
        route = ROOT_ROUTE
    ) {
        composable(route = Screen.OnboardScreen.route) {
            OnboardScreen(navController)
        }
        onboardingNavGraph(navController)
        homeNavGraph(navController)
        bottomNavGraph(navController)
    }
}