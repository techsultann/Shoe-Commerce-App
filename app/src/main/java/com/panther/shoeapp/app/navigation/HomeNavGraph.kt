package com.panther.shoeapp.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.panther.shoeapp.ui.presentation.home.HomeScreen
import com.panther.shoeapp.utils.HOME_ROUTE
import com.panther.shoeapp.utils.Screen


fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {

    navigation(
        startDestination = Screen.HomeScreen.route,
        route = HOME_ROUTE
    ){

        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController)
        }

    }

}